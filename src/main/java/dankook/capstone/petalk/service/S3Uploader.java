package dankook.capstone.petalk.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket; //S3 버킷 이름

    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)    //파일 변환할 수 없으면 에러
                .orElseThrow(() -> new IllegalArgumentException("error:" +
                        "MultipartFile -> File convert fail"));

        return upload(uploadFile, dirName);
    }

    //s3로 파일 업로드하기
    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName(); //s3에 저장된 파일 이름

        String uploadImageUrl = putS3(uploadFile, fileName);    //s3로 업로드

        removeNewFile(uploadFile);

        return uploadImageUrl;
    }

    //s3로 업로드
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName,
                uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));

        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    //로컬에 저장된 이미지 지우기
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("File delete success");
            return;
        }
        log.info("File delete fail");
    }

    //해당 메소드에서 로컬 프로젝트에 사진 파일이 생성되지만, removeNewFile()을 통해서 바로 지우고 있는 로직이다.
    //System.getProperty("user.dir"): 현재 프로젝트의 절대 경로를 꺼내온다.
    //즉, 프로젝트 루트 경로 아래에 업로드한 파일이 생성되며 바로 removeNewFile을 통해서 로커레 있는 파일은 삭제한다.
    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());

        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }
}
