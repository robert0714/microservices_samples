package develop.ptx.common.services;

import java.nio.charset.StandardCharsets;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.beanutils.PropertyUtils; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
 

public class CommonServices {

	
	
	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	/** 取得當下UTC時間 */
	protected String getServerTime() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dateFormat.format(calendar.getTime());
	}
	protected HttpHeaders createHttpHeaders(final String appID,final  String appKey) {
		//取得當下的UTC時間，Java8有提供時間格式DateTimeFormatter.RFC_1123_DATE_TIME
        //但是格式與C#有一點不同，所以只能自行定義
		String xdate = getServerTime();
		String signDate = "x-date: " + xdate;
		String signature = "";
		try {
			// 取得加密簽章
			signature = signature(signDate, appKey);
		} catch (SignatureException e) {
			LOGGER.error(e.getMessage() ,e);
		}
		
		LOGGER.debug("Signature :" + signature);
//		String sAuthTemplate="hmac username=\"%s\", algorithm=\"hmac-sha1\", headers=\"x-date\", signature=\"%s\"";
        String sAuth = "hmac username=\"" + appID + "\", algorithm=\"hmac-sha1\", headers=\"x-date\", signature=\"" + signature + "\"";
//	    String sAuth = String.format(sAuthTemplate, appID,appKey);
	    
        LOGGER.debug(sAuth);
		
		
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);	
		
	    headers.add("Authorization", sAuth);
	    headers.add("x-date", xdate);
	    headers.add("Accept-Encoding", "gzip");
	    
	    return headers; 
	}
	protected <T> T buildFallback(Class<T> clazz )   {
		LOGGER.error("PTX主機連線不通！！！");
		LOGGER.error("executing.........buildFallbackptxHystrix");
		 
		T response = null;
		try {  
			 response = BeanUtils.instantiateClass(clazz);
			 develop.ptx.bike.model.Error error =new  develop.ptx.bike.model. Error();
			 error.setMsg("PTX伺服器連線不通");
			 PropertyUtils.setProperty(response, "error", error);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(), e);
		}
		LOGGER.error("finish.........buildFallbackptxHystrix");
		return response;
	}
	protected  String signature(String xData, String appKey) throws java.security.SignatureException {
		try {
			final Base64.Encoder encoder = Base64.getEncoder();
			// get an hmac_sha1 key from the raw key bytes
			SecretKeySpec signingKey = new SecretKeySpec(appKey.getBytes(StandardCharsets.UTF_8), "HmacSHA1");

			// get an hmac_sha1 Mac instance and initialize with the signing key
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(signingKey);

			// compute the hmac on input data bytes
			byte[] rawHmac = mac.doFinal(xData.getBytes(StandardCharsets.UTF_8));
			String result = encoder.encodeToString(rawHmac);
			return result;

		} catch (Exception e) {
			throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
		}
	}
}
