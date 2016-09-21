package util;


public enum EnvironmentType {

  LOCAL("develop.q4desktop.com/"),
  DEVELOP("develop.q4desktop.com/"),
  STAGING("staging.q4desktop.com/"),
  BETA("beta.q4desktop.com/"),
  PRODUCTION("www.q4desktop.com/"),
  // Admin
  LOCALADMIN("q4admin-develop.s3-website-us-east-1.amazonaws.com/"),
  DEVELOPADMIN("q4admin-develop.s3-website-us-east-1.amazonaws.com/"),
  STAGINGADMIN("q4admin-staging.s3-website-us-east-1.amazonaws.com/");
  private final String host;
  private final String protocol = "http://";

  EnvironmentType(String host) {
    this.host = host;
  }

  public String getHost() {
    return host;
  }

  public String getProtocol() {
    return protocol;
  }
}

