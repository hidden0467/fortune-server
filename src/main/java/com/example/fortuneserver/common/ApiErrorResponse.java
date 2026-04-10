package com.example.fortuneserver.common;

import java.util.List;

public record ApiErrorResponse(String error, List<String> details) {
}
