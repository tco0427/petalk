package dankook.capstone.petalk.data;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class ResponseData<T> {
    @ApiModelProperty(value = "상태코드")
    private int status;

    @ApiModelProperty(value = "메세지")
    private String message;

    @ApiModelProperty(value = "JSON 응답값")
    private T data;

    public ResponseData() {
        this.status = StatusCode.OK;
        this.message = ResponseMessage.SUCCESS;
        this.data = null;
    }

    public ResponseData(T data) {
        this.data = data;
    }
}
