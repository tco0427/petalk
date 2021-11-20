package dankook.capstone.petalk.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/auth/kakao")
public class KakaoController {
    @RequestMapping("")
    @ResponseBody
    public String redirectKakaoLogin() {
        return "<!doctype html>\n"
                + "<html lang=\"ko\">\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <meta name=\"viewport\"\n"
                + "          content=\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\">\n"
                + "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n"
                + "    <title>카카오 코드 발급</title>\n"
                + "</head>\n"
                + "<body>"
                + "<div>"
                + "<img style='"
                + "    margin: 0;\n"
                + "    position: absolute;\n"
                + "    top: 50%;\n"
                + "    -ms-transform: translateY(-50%);\n"
                + "    transform: translate(-50%, -50%);\n"
                + "    width: 18%;\n"
                + "    left: 50%;' src='https://c.tenor.com/I6kN-6X7nhAAAAAj/loading-buffering.gif'></div>";
    }
}