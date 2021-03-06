package com.hoang.backend_learning.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.json.JSONObject;

import java.util.*;

@Data
@Accessors(chain = true)
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorsDto {
    public static final String EMPTY = "require";
    public static final String INVALID = "invalid";
    public static final String USED = "used";
    public static final String BLOCKED = "blocked";

    Map<String, Object> errors;

    protected ErrorsDto(Map<String, Object> errors) {
        this.errors = errors;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        private final Map<String, List<String>> errors = new HashMap<>();

        public Builder() {

        }

        public Builder(Map<String, List<String>> errors) {
            this.errors.putAll(errors);
        }

        public Builder addEmptyField(String field) {
            return this.addErrorField(field, Collections.singletonList(EMPTY));
        }

        public Builder addUsedField(String field) {
            return this.addErrorField(field, Collections.singletonList(USED));
        }

        public Builder addInvalidField(String field) {
            return this.addErrorField(field, Collections.singletonList(INVALID));
        }

        public Builder addBlockedField(String field) {
            return this.addErrorField(field, Collections.singletonList(BLOCKED));
        }

        public Builder addErrorField(String field, List<String> errors) {
            this.errors.put(field, errors);
            return this;
        }

        public ErrorsDto build() {
            JSONObject json = new JSONObject();

            for (Map.Entry<String, List<String>> error : errors.entrySet()) {
                String[] keyLevels = error.getKey().split("\\.");
                JSONObject innerJson = json;

                for (int i = 0; i < keyLevels.length; i++) {
                    if (!innerJson.has(keyLevels[i])) {
                        innerJson.put(keyLevels[i], new JSONObject());
                        if (i == keyLevels.length - 1) {
                            innerJson.put(keyLevels[i], error.getValue());
                            break;
                        }
                    }
                    innerJson = innerJson.getJSONObject(keyLevels[i]);
                }
            }
            return new ErrorsDto(json.toMap());
        }
    }
}
