package co.istad.dealkh.base;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasedErrorResponse<T> {
    private BasedError<T> error;
}
