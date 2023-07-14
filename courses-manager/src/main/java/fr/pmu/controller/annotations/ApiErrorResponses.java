package fr.pmu.controller.annotations;

import fr.pmu.exceptions.ApiError;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ApiResponses({@ApiResponse(
        responseCode = "400",
        description = "Bad Request List of supported error codes: - 20: Invalid URL parameter value - 21: Missing body - 22: Invalid body - 23: Missing body field - 24: Invalid body field - 25: Missing header - 26: Invalid header value - 27: Missing query-string parameter - 28: Invalid query-string parameter value",
        content = {
                @Content(mediaType = "application/json",
                schema = @Schema(implementation = ApiError.class))
        }
), @ApiResponse(
        responseCode = "404",
        description = "Not Found  List of supported error codes: - 60 : Resource not found",
        content = {
                @Content(mediaType = "application/json",
                schema = @Schema(implementation = ApiError.class))
        }
), @ApiResponse(
        responseCode = "405",
        description = "Method Not Allowed",
        content = {
                @Content(mediaType = "application/json",
                schema = @Schema(implementation = ApiError.class))
        }
), @ApiResponse(
        responseCode = "500",
        description = "Internal Server ApiError",
        content = {
                @Content(mediaType = "application/json",
                schema = @Schema(implementation = ApiError.class))
        }
)})
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiErrorResponses {
}
