/**
 * Open Banking Gateway FinTech Example API
 * This is a sample API that show how develop FinTech use case that invoke banking APIs.  #### User Agent This Api assumes that the PsuUserAgent is a modern browsers that * automatically detects the \"302 Found\" response code and proceeds with the associated location url, * stores httpOnly cookies sent with the redirect under the given domain and path as defined by [RFC 6265](https://tools.ietf.org/html/rfc6265).  This Api also assumes any other PsuUserAgent like a native mobile or a desktop application can simulate this same behavior of a modern browser with respect to 30X and Cookies.  #### SessionCookies and XSRF After a PSU is authenticated with the FinTech environment (either through the simple login interface defined here, or through an identity provider), the FinTechApi will establish a session with the FinTechUI. This is done by the mean of using a cookie called SessionCookie. This SessionCookie is protected by a corresponding XSRF-TOKEN. * The request that sets a SessionCookie also carries a corresponding X-XSRF-TOKEN in the response header. * It is the responsibility of the FinTechUI to parse this X-XSRF-TOKEN and send it back to the FinTechApi with each subsequen request.  #### Redirecting to the ConsentAuthorisationApi Any response of the FinTechApi that redirects the PSU to the ConsentAuthorisationApi makes sure following happens: * that the exisitng SessionCookie is deleted, as there is no explicite login. * that a RedirectCookie is set, so the user can be authenticated again when sent back to the FinTechApi. * The url that sends the user back to the FinTechApi must carry a redirecState parameter that matches the corresponding redirect cookie.  While redirecting the user to the ConsentAuthorisationApi, there is no certainty upon how long the consent session will take. For this reason, it is better to set a separated RedirectSessionCookie that has a life set to the expected max dureation of the consent authorisation session.  #### Reloading the FinTechUI Reloading the FinTechUI, we will also loose the XSRF parameter that is used to validate the SessionCookie. This is why we set RedirectCookie (that this time has a very short life span). The url reloading the FinTechUI must carry a redirectState parameter that will be used to invoke the /afterReload endpoint of the FinTechApi. Thus leading to a new SessionCookie and corresponding XSRF parameter. 
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
import { AccountStatus } from './accountStatus';
import { Address } from './address';
import { LinksAccountDetails } from './linksAccountDetails';


/**
 * The ASPSP shall give at least one of the account reference identifiers:   - iban   - bban   - pan   - maskedPan   - msisdn If the account is a multicurrency account currency code in \"currency\" is set to \"XXX\". 
 */
export interface AccountDetails { 
    /**
     * This shall be filled, if addressable resource are created by the ASPSP on the /accounts or /card-accounts endpoint.
     */
    resourceId?: string;
    /**
     * International bank account number ISO 31616.
     */
    iban?: string;
    /**
     * Basic Bank Account Number (BBAN) Identifier.  This data element can be used in the body of the Consent request.   Message for retrieving Account access Consent from this Account. This   data elements is used for payment Accounts which have no IBAN.   ISO20022: Basic Bank Account Number (BBAN).    Identifier used nationally by financial institutions, i.e., in individual countries,   generally as part of a National Account Numbering Scheme(s),   which uniquely identifies the account of a customer. 
     */
    bban?: string;
    /**
     * Primary Account Number according to ISO/IEC 7812. 
     */
    pan?: string;
    /**
     * Masked Primary Account Number. 
     */
    maskedPan?: string;
    /**
     * Mobile phone number.
     */
    msisdn?: string;
    /**
     * ISO 4217 Alpha 3 currency code. 
     */
    currency: string;
    /**
     * Name of the account given by the bank or the PSU in online-banking.
     */
    name?: string;
    /**
     * Product name of the bank for this account, proprietary definition.
     */
    product?: string;
    /**
     * ExternalCashAccountType1Code from ISO 20022. 
     */
    cashAccountType?: string;
    status?: AccountStatus;
    /**
     * BICFI 
     */
    bic?: string;
    /**
     * Case of a set of pending card transactions, the APSP will provide the relevant cash account the card is set up on.
     */
    linkedAccounts?: string;
    /**
     * Specifies the usage of the account:   * PRIV: private personal account   * ORGA: professional account 
     */
    usage?: AccountDetails.UsageEnum;
    /**
     * Specifications that might be provided by the ASPSP:   - characteristics of the account   - characteristics of the relevant card 
     */
    details?: string;
    links?: LinksAccountDetails;
    /**
     * Name of the legal account owner. If there is more than one owner, then e.g. two names might be noted here.
     */
    ownerName?: string;
    ownerAddress?: Address;
}
export namespace AccountDetails {
    export type UsageEnum = 'PRIV' | 'ORGA';
    export const UsageEnum = {
        PRIV: 'PRIV' as UsageEnum,
        ORGA: 'ORGA' as UsageEnum
    };
}


