package util;


public enum EnvironmentType {

  LOCAL("develop.q4desktop.com/"),
  DEVELOP("develop.q4desktop.com/"),
  STAGING("staging.q4desktop.com/"),
  BETA("beta.q4desktop.com/"),
  PRODUCTION("www.q4desktop.com/");

  private final String host;
  private final String protocol = "https://";

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

