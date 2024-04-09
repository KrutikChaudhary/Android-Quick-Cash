package com.example.group12.util;

/**
 * Interface for handling merchant ID availability callbacks.
 */
public interface MerchantIDCallBack {
    /**
     * Method called when the availability of a merchant ID is determined.
     *
     * @param isValid    Indicates whether the merchant ID is valid or not.
     * @param merchantID The merchant ID being checked for availability.
     */
    void merchantIdAvailableResult(boolean isValid, String merchantID);
}

