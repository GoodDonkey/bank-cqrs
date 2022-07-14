package query.query;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) // required by Jackson
public class AccountQuery {
    private String holderId;
}
