package co.edu.javeriana.eas.patterns.providers.exceptions;

import co.edu.javeriana.eas.patterns.common.enums.EExceptionCode;
import co.edu.javeriana.eas.patterns.common.exceptions.QuotationCoreException;

public class AdvisorException extends QuotationCoreException {

    public AdvisorException(EExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    public AdvisorException(EExceptionCode exceptionCode, String causeMessage) {
        super(exceptionCode, causeMessage);
    }

}