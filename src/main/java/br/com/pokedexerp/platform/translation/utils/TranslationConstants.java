package br.com.pokedexerp.platform.translation.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TranslationConstants {

    public static final String UNAUTHORIZED = "platform.authentication.unauthorized";
    public static final String ACCESS_DENIED = "platform.authentication.access_denied";
    public static final String INVALID_CREDENTIALS = "platform.authentication.invalid_credentials";
    public static final String USER_NOT_FOUND = "platform.authentication.user_not_found";
    public static final String USER_BASE_ALREADY_INITIALIZED = "platform.authentication.user_base_already_initialized";
    public static final String ROLE_NOT_FOUND = "platform.authentication.role_not_found";
    public static final String INVALID_FILTER = "platform.entity.invalid_filter";
    public static final String INVALID_ORDER_BY = "platform.entity.invalid_order_by";
    public static final String OPERATION_INVALID_FOR_ADMIN = "entity.user.operation_invalid_for_admin";
    public static final String OPERATION_INVALID_FOR_SAME_USER = "entity.user.operation_invalid_for_same_user";
}
