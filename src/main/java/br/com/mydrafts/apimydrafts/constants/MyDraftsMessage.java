package br.com.mydrafts.apimydrafts.constants;

public class MyDraftsMessage {

    private MyDraftsMessage() {}

    public static final String DRAFT_CONFLICT = "Draft already registered";
    public static final String DRAFT_NOT_FOUND = "Draft not found";
    public static final String DRAFT_TV_BAD_REQUEST = "The season cannot be null";
    public static final String DRAFT_TV_SEASON = "There is no such season on this tv show";
    public static final String FAVORITE_CONFLICT = "Favorite already registered";
    public static final String FAVORITE_NOT_FOUND = "Favorite not found";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String EMAIL_NOT_FOUND = "Email not found";
    public static final String EMAIL_CONFLICT = "Email is conflict";
    public static final String PASSWORD_INCORRECT = "Password incorrect";
    public static final String TOKEN_INVALID = "Token Invalid";
    public static final String USER_UNAUTHORIZED = "User without permission";

}
