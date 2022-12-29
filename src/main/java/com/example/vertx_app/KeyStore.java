package com.example.vertx_app;

public class KeyStore {

  private static final String publicKey = //"-----BEGIN PUBLIC KEY-----\n" +
    "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApzY9brM8bjd3RVc+pNCd" +
    "lIhT8PduqzUKUVyn0mFaJqSusxSB5KBevmLmCeclISjZ2+2pSx7JHyYmgGLhvpLT" +
    "NV9kj7yuyaRJ8gmRtNBpy5rLzQrEHtAO801voiUelh69BSZoBV7iRaytQ7oIsCqg" +
    "8KLIyD8x1vOVw06p0PUQv+N5kADtYM5IfAljNt3EKl9P8Pj4/j4XbLGq4rz1T12l" +
    "lkx/cxFdlfdUy/26axoroXKxayQfASJid5o4o4O9FZY4nXRzbILU5pAl1ulG8fi0" +
    "46P28dFv6/m5GU/DOWhxQhHlUPIvzNKaU+fANVyVEFuHxGU6xsUiW20Nv6GUC0Rw" +
    "WQIDAQAB";// +
  //"-----END PUBLIC KEY-----";

  private static final String privateKey = "-----BEGIN RSA PRIVATE KEY-----\n" +
                                           "MIIEowIBAAKCAQEApzY9brM8bjd3RVc+pNCdlIhT8PduqzUKUVyn0mFaJqSusxSB\n" +
                                           "5KBevmLmCeclISjZ2+2pSx7JHyYmgGLhvpLTNV9kj7yuyaRJ8gmRtNBpy5rLzQrE\n" +
                                           "HtAO801voiUelh69BSZoBV7iRaytQ7oIsCqg8KLIyD8x1vOVw06p0PUQv+N5kADt\n" +
                                           "YM5IfAljNt3EKl9P8Pj4/j4XbLGq4rz1T12llkx/cxFdlfdUy/26axoroXKxayQf\n" +
                                           "ASJid5o4o4O9FZY4nXRzbILU5pAl1ulG8fi046P28dFv6/m5GU/DOWhxQhHlUPIv\n" +
                                           "zNKaU+fANVyVEFuHxGU6xsUiW20Nv6GUC0RwWQIDAQABAoIBAHIYEobsiflyo1GP\n" +
                                           "HFF9f2iQNSagzsTHpkip/mEQyGDB0eqjvZXwz3T9KpXrQGyF4VGtsuxtDzLP50mH\n" +
                                           "Lx3INZGfi5CAYIt6LHKYkPFdnDKvZwx7oiKcOPdPCTMMPPiV9MgE0smHWsHL91XV\n" +
                                           "JISAMdzAVlw8spdHEhN4I2tPLKShSkHV+n1abWHePwcrsMvGdDVkysfW5RlaqdGb\n" +
                                           "cT5fbxw8hFrVC4iXeKxbtoMlTpiqQjQz/kVm3LmY1aIGfSASkcthnmt+T95gPX08\n" +
                                           "qzcgDwyHKOkJTBbfLEWPqc8X65CCCr4C9uCcOP3+JikvW3KhDdgHajKTWqwrL0A/\n" +
                                           "S+FdeOECgYEAzzNzid/wfsIZlmkhIR1VPCM7e8uR2Pzsb+tkqQFpRamiireLJ5Zu\n" +
                                           "p+UvjoENErNS2lZkT9SoWz4fPsems990KCkH97fH61NNY3926BVLiw6iIvdsrB+o\n" +
                                           "bJd7rNrCukG0s02ktnO+Tpyz+T8wuTSfwiIl4xt4+/zbF5N2ptSPFGUCgYEAzpfE\n" +
                                           "lcJaN54Agt2uk7wJyozrLuEqXdRssiSNSBeWnhMLesx8BM04CmNRF4d4xS1SJPg0\n" +
                                           "VwDBgcQl6///V/g0TdvfpMq+ThwPIk2WetKsdsfuOssyjV24J6Wbzht/VQ9I9+UQ\n" +
                                           "7H+Q+NHfhOyus03e9xP9xQvpQM8hnFfAvjK6SuUCgYA2WDNUlTS5umUFqzsyOURS\n" +
                                           "ypbeaE0vkV7eF+pF3YXj7JMiOPO5hV4ukREBGl34aqovc/tJaOhjTg7Z43mYseIA\n" +
                                           "aoqZcU1xLl8c2qCwTG2M4mCqNST+nCsLmEuq0ZHDUlp3sMSjygW/DqCxnd5EpUXF\n" +
                                           "oDk5sMnxpac9LsMvDI8edQKBgQCcKlVL73JLAIDaa8B/pHCyT/unoEVqUB0qPD8f\n" +
                                           "89TDARp4ZQKKjqaCE7lE0/8S7cNedtD5w3SDJ0aTMxxN3y2KfV59L1TTrpoyD/xk\n" +
                                           "M8RytYb7ooZAHkwKjEFOd7K1FazO1rUIURXBQMR5soAMwhx9IPt/bBWSDAEYMa1d\n" +
                                           "GPOBkQKBgFQ/8woIGjOppUQucKw5WyN0JpVyXo0ZSo0X/xFhfor76giwC26P0XDT\n" +
                                           "XFTn66bo5KppKA7baq1KJsS9ohoGlZb0CjFAz6+Is7eGciKEXbmF/ohL8iVtoy14\n" +
                                           "BCZUxfWIpd9EMndqML/Iy3epueP8+GXqHDbaGospO2DUCIYS43hd\n" +
                                           "-----END RSA PRIVATE KEY-----";

  public static String getPublicKey(){
    return publicKey;
  }

  public static String getPrivateKey(){
    return privateKey;
  }
}
