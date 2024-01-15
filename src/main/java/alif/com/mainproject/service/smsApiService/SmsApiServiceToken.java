package alif.com.mainproject.service.smsApiService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SmsApiServiceToken {
    private String message;
    private SmsApiServiceTokenData data;
    private String tokenType;
}
