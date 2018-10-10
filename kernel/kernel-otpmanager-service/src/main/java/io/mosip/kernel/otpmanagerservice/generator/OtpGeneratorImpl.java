package io.mosip.kernel.otpmanagerservice.generator;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.kamranzafar.otp.provider.TOTPProvider;

import io.mosip.kernel.core.spi.otpmanager.OtpGenerator;
import io.mosip.kernel.otpmanagerservice.constant.OtpCryptoConstants;
import io.mosip.kernel.otpmanagerservice.constant.OtpErrorConstants;
import io.mosip.kernel.otpmanagerservice.constant.OtpPropertyConstants;
import io.mosip.kernel.otpmanagerservice.exception.MosipResourceNotFoundException;

/**
 * This class provides the implementation for OTP Generation.
 * 
 * @author Ritesh Sinha
 * @version 1.0.0
 *
 */
public class OtpGeneratorImpl implements OtpGenerator {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.mosip.kernel.otpmanagerapiwrapper.generator.OtpGenerator#generateOtp()
	 */
	@Override
	public String generateOtp() {
		// Length of OTP.
		String otpLength;
		// The crypto function to use.
		String authenticationCode;
		// The shared secret, HEX encoded.
		String shareKey;
		// The numeric string in base 10.
		String generatedOtp;
		ResourceBundle resource = null;
		try {
			resource = ResourceBundle.getBundle(OtpPropertyConstants.OTP_FILE.getProperty());
		} catch (MissingResourceException exception) {
			throw new MosipResourceNotFoundException(OtpErrorConstants.OTP_GEN_RESOURCE_NOT_FOUND.getErrorCode(),
					OtpErrorConstants.OTP_GEN_RESOURCE_NOT_FOUND.getErrorMessage());
		}
		otpLength = resource.getString(OtpPropertyConstants.OTP_LENGTH.getProperty());
		authenticationCode = OtpCryptoConstants.OTP_CRYPTO_SHA512.getCryptoType();
		shareKey = resource.getString(OtpPropertyConstants.SHARE_KEY.getProperty());
		generatedOtp = TOTPProvider.generateTOTP(shareKey, String.valueOf(System.currentTimeMillis()),
				String.valueOf(otpLength), authenticationCode);
		return generatedOtp;
	}
}
