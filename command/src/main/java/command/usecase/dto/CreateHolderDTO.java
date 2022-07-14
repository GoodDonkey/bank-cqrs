package command.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateHolderDTO {
    
    @NotBlank
    private String holderName;
    
    @NotBlank
    private String phoneNumber;
    
    @NotBlank
    private String address;
    
    @NotBlank
    private String company;
}
