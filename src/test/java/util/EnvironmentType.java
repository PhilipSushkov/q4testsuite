package util;


public enum EnvironmentType {

  DEVELOP("q4desktop-develop.s3-website-us-east-1.amazonaws.com/"),
  BETA("q4desktop-staging.s3-website-us-east-1.amazonaws.com/"),
  PRODUCTION("");

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

