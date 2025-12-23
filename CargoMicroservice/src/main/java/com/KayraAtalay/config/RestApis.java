package com.KayraAtalay.config;

public class RestApis {

    public static final String DEVELOPER = "/dev";
    public static final String VERSIONS = "/v1";
    public static final String CARGO = DEVELOPER + VERSIONS + "/logi-track-cargo";

    public static final String SAVE_CARGO = "/create-cargo";
    public static final String CANCEL_CARGO = "/cancel-cargo{cargoId}";
    public static final String UPDATE_CARGO_STATUS = "/update-cargo-status";
    public static final String ACCEPT_CARGO = "/accept-cargo";
    public static final String DELIVER_CARGO = "/deliver-cargo";
    public static final String FIND_CARGO_BY_TRACKING_NUMBER = "/find-cargo-by-tracking-number";
    public static final String FIND_USER_CARGOS = "/find-user-cargos";

}




