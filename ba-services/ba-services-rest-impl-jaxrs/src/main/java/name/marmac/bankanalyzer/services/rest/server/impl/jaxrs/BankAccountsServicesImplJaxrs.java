package name.marmac.bankanalyzer.services.rest.server.impl.jaxrs;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import name.marmac.bankanalyzer.dal.api.BankAccountsPersistenceServices;
import name.marmac.bankanalyzer.model.api.BankAccountPO;
import name.marmac.bankanalyzer.model.api.TransactionPO;
import name.marmac.bankanalyzer.model.to.bankaccounts.BankAccountTOType;
import name.marmac.bankanalyzer.model.to.bankaccounts.BankAccountsTOType;
import name.marmac.bankanalyzer.model.to.bankaccounts.ObjectFactory;
import name.marmac.bankanalyzer.model.to.transactions.TransactionTOType;
import name.marmac.bankanalyzer.model.to.transactions.TransactionsTOType;
import name.marmac.bankanalyzer.services.rest.properties.BankAnalyzerProperties;
import name.marmac.tutorials.cxfatwork.services.web.rest.api.customerservice.BankAccountsServices;
import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.cxf.jaxrs.ext.search.SearchBean;
import org.apache.cxf.jaxrs.ext.search.SearchCondition;
import org.apache.cxf.jaxrs.ext.search.SearchContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by marcomaccio on 10/09/2015.
 */
@Api(value = "/bankaccountsservices", description = "Bank Accounts Services - CRUD")
@Path("/bankaccountsservices")
@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
public class BankAccountsServicesImplJaxrs implements BankAccountsServices {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountsServicesImplJaxrs.class);

    private static final String PATH_PARAM_IBAN                 = "iban";
    private static final String QUERY_PARAM_IBAN                = "iban";
    private static final String QUERY_PARAM_LIMIT               = "limit";
    private static final String QUERY_PARAM_HOLDERNAME          = "holderName";
    private static final String QUERY_PARAM_OPENINGDATE         = "openingDate";
    private static final String QUERY_PARAM_CREATEDDATE         = "createdDate";
    private static final String QUERY_PARAM_LASTUPDATE          = "lastUpdate";
    private static final String QUERY_PARAM_EXECUTIONDATE       = "executionDate";
    private static final String QUERY_PARAM_VALUEDATE           = "valueDate";
    private static final String QUERY_PARAM_DEBIT               = "debit";
    private static final String QUERY_PARAM_CREDIT              = "credit";
    private static final String QUERY_PARAM_CURRENCY            = "currency";

    private static final String ACCESS_CONTROL_ALLOW_ORIGIN_ALL = "*";

    //JAX-RS and JAX-WS context
    @Context
    private MessageContext context = null;
    @Context
    private SearchContext  searchContext;

    private name.marmac.bankanalyzer.model.to.bankaccounts.ObjectFactory bankAccountsObjectFactory;
    private name.marmac.bankanalyzer.model.to.transactions.ObjectFactory transactionsObjectFactory;
    private BankAnalyzerProperties              bankAnalyzerProperties;
    private BankAccountsPersistenceServices     bankAccountsPersistenceServices;

    /**
     *
     */
    public BankAccountsServicesImplJaxrs(){
        LOGGER.debug("Initializing Bank Accounts Services Impl JaxRS ");
    }

    /** Getters & Setters - START **/
    public ObjectFactory getBankAccountsObjectFactory() {
        return bankAccountsObjectFactory;
    }

    /**
     *
     * @param bankAccountsObjectFactory
     */
    public void setBankAccountsObjectFactory(ObjectFactory bankAccountsObjectFactory) {
        this.bankAccountsObjectFactory = bankAccountsObjectFactory;
    }

    /**
     *
     * @return
     */
    public name.marmac.bankanalyzer.model.to.transactions.ObjectFactory getTransactionsObjectFactory() {
        return transactionsObjectFactory;
    }

    /**
     *
     * @param transactionsObjectFactory
     */
    public void setTransactionsObjectFactory(name.marmac.bankanalyzer.model.to.transactions.ObjectFactory transactionsObjectFactory) {
        this.transactionsObjectFactory = transactionsObjectFactory;
    }

    /**
     *
     * @return
     */
    public BankAnalyzerProperties getBankAnalyzerProperties() {
        return bankAnalyzerProperties;
    }

    /**
     *
     * @param bankAnalyzerProperties
     */
    public void setBankAnalyzerProperties(BankAnalyzerProperties bankAnalyzerProperties) {
        this.bankAnalyzerProperties = bankAnalyzerProperties;
    }

    /**
     *
     * @return
     */
    public BankAccountsPersistenceServices getBankAccountsPersistenceServices() {
        return bankAccountsPersistenceServices;
    }

    /**
     *
     * @param bankAccountsPersistenceServices
     */
    public void setBankAccountsPersistenceServices(BankAccountsPersistenceServices bankAccountsPersistenceServices) {
        this.bankAccountsPersistenceServices = bankAccountsPersistenceServices;
    }

    public void setSearchContext(SearchContext searchContext){
        this.searchContext = searchContext;
    }

    /** Getters & Setters - END **/

    @Override
    public BankAccountTOType createBankAccount(BankAccountTOType bankaccounttotype) {

        return null;
    }

    /**
     *
     * @param limit
     * @param iban
     * @param holdername
     * @param openingDate
     * @param createdDate
     * @param lastUpdate
     * @return
     */
    @Override
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/bankaccounts")
    @ApiOperation(value = "Get BankAccounts filtering them by some parameters in AND condition",
            notes = "Retrieve method",
            response = BankAccountsTOType.class)
    public BankAccountsTOType getBankAccountsByQuery(@ApiParam(value = QUERY_PARAM_LIMIT,       required = false, allowMultiple = true) @QueryParam(QUERY_PARAM_LIMIT)          Integer limit,
                                                     @ApiParam(value = QUERY_PARAM_IBAN,        required = false, allowMultiple = true) @QueryParam(QUERY_PARAM_IBAN)           String iban,
                                                     @ApiParam(value = QUERY_PARAM_HOLDERNAME,  required = false, allowMultiple = true) @QueryParam(QUERY_PARAM_HOLDERNAME)     String holdername,
                                                     @ApiParam(value = QUERY_PARAM_OPENINGDATE, required = false, allowMultiple = true) @QueryParam(QUERY_PARAM_OPENINGDATE)    String openingDate,
                                                     @ApiParam(value = QUERY_PARAM_CREATEDDATE, required = false, allowMultiple = true) @QueryParam(QUERY_PARAM_CREATEDDATE)    String createdDate,
                                                     @ApiParam(value = QUERY_PARAM_LASTUPDATE,  required = false, allowMultiple = true) @QueryParam(QUERY_PARAM_LASTUPDATE)     String lastUpdate) {


        //Retrieve the bankaccount from the Persistence Layer
        List<BankAccountPO> bankAccountPOList = bankAccountsPersistenceServices.getAllBankAccounts();
        //Convert the Persistence Object to the TransferObject

        return convertToBankAccountsTOType(bankAccountPOList);
    }

    @Override
    public BankAccountsTOType updateBankAccounts(BankAccountsTOType bankaccountstotype) {
        BankAccountsTOType bankAccountsTOType = bankAccountsObjectFactory.createBankAccountsTOType();

        return bankAccountsTOType;
    }

    @Override
    public BankAccountsTOType deleteBankAccounts(BankAccountsTOType bankaccountstotype) {
        BankAccountsTOType bankAccountsTOType = bankAccountsObjectFactory.createBankAccountsTOType();

        return bankAccountsTOType;
    }

    @Override
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/bankaccounts/{iban}")
    @ApiOperation(value = "Get BankAccount by IBAN",
            notes = "Retrieve method of a specific Bank Account",
            response = BankAccountTOType.class)
    public BankAccountTOType getBankAccountByNativeId(@ApiParam(value = PATH_PARAM_IBAN, required = true) @PathParam("iban") String iban) {

        LOGGER.debug("getBankAccountByNativeId Method: search for BankAccount with IBAN=" + iban);

        BankAccountTOType bankAccountTOType = bankAccountsObjectFactory.createBankAccountTOType();
        BankAccountPO bankAccountPO = bankAccountsPersistenceServices.getBankAccountByNativeId(iban);
        if (bankAccountPO != null) {
            bankAccountTOType = this.convertToBankAccountTOType(bankAccountPO);
            context.getHttpServletResponse().setHeader("Access-Control-Allow-Origin", ACCESS_CONTROL_ALLOW_ORIGIN_ALL);
        }
        return bankAccountTOType;
    }

    @Override
    public BankAccountTOType updateBankAccountByNativeId(@PathParam("id") String id, BankAccountTOType bankaccounttotype) {
        BankAccountTOType bankAccountTOType = bankAccountsObjectFactory.createBankAccountTOType();

        return bankAccountTOType;
    }

    @Override
    public BankAccountTOType deleteBankAccountByNativeId(@PathParam("id") String id) {
        BankAccountTOType bankAccountTOType = bankAccountsObjectFactory.createBankAccountTOType();

        return bankAccountTOType;
    }

    @Override
    @GET
    @Produces({"application/xml", "application/json" })
    @Path("/bankaccounts/{iban}/transactions")
    @ApiOperation(value = "Get Transactions for a given Bank Account's iban, filtering them by some parameters in AND condition",
                    notes = "Retrieve method",
                    response = TransactionsTOType.class)
    public TransactionsTOType getTransactionsByBankAccount(@ApiParam(value = PATH_PARAM_IBAN,           required = true)                        @PathParam(PATH_PARAM_IBAN)     String iban,
                                                           @ApiParam(value = QUERY_PARAM_LIMIT,         required = false, allowMultiple = true) @QueryParam(QUERY_PARAM_LIMIT)  Integer limit,
                                                           @ApiParam(value = QUERY_PARAM_EXECUTIONDATE, required = false, allowMultiple = true) @QueryParam("executionDate") Date executionDate,
                                                           @ApiParam(value = QUERY_PARAM_VALUEDATE,     required = false, allowMultiple = true) @QueryParam("valueDate") String valueDate,
                                                           @ApiParam(value = QUERY_PARAM_DEBIT,         required = false, allowMultiple = true) @QueryParam("debit") BigInteger debit,
                                                           @ApiParam(value = QUERY_PARAM_CREDIT,        required = false, allowMultiple = true) @QueryParam("credit") BigInteger credit,
                                                           @ApiParam(value = QUERY_PARAM_CURRENCY,      required = false, allowMultiple = true) @QueryParam("currency") String currency) {

        LOGGER.debug("getTransactionsByBankAccount");
        SearchCondition<SearchBean> sc = searchContext.getCondition(SearchBean.class);
        LOGGER.debug(sc.toString());

        List<TransactionPO> transactionsPO = bankAccountsPersistenceServices.getAllTransactionsByBankAccount(iban);
        //Convert the TransactionsPO (list) into TransactionsTO (list)
        TransactionsTOType transactionsTO = convertToTransactionsTOType(transactionsPO);
        LOGGER.debug("returning " + transactionsTO.getTransactions().size() + " transactions");
        return transactionsTO;
    }


    /**
     * Convert a list of BankAccountPO into a BankAccountsTOType
     * @param bankAccountPOList
     * @return
     */
    private BankAccountsTOType convertToBankAccountsTOType(List<BankAccountPO> bankAccountPOList){
        BankAccountsTOType bankAccountsTOType = getBankAccountsObjectFactory().createBankAccountsTOType();
        for (BankAccountPO bankAccountPO : bankAccountPOList){
            //get each BankAccountPO and convert into a BankAccountTOType
            BankAccountTOType bankAccountTOType = convertToBankAccountTOType(bankAccountPO);
            //add the bankAccountTOType to the list BankAccountsTOType
            bankAccountsTOType.getBankaccounts().add(bankAccountTOType);
        }
        bankAccountsTOType.setTotalRecords(bankAccountsTOType.getBankaccounts().size());
        return bankAccountsTOType;
    }

    /**
     * Convert a single BankAccountPO into a BankAccountTOType
     * @param bankAccountPO
     * @return
     */
    private BankAccountTOType convertToBankAccountTOType(BankAccountPO bankAccountPO){
        BankAccountTOType bankAccountTOType = getBankAccountsObjectFactory().createBankAccountTOType();
        bankAccountTOType.setIBAN(bankAccountPO.getIban());
        bankAccountTOType.setHoldername(bankAccountPO.getHolderName());

        Calendar cal = Calendar.getInstance();

        cal.setTime(bankAccountPO.getOpeningDate());
        bankAccountTOType.setOpeningDate(cal);

        cal.setTime(bankAccountPO.getCreatedDate());
        bankAccountTOType.setCreatedDate(cal);

        cal.setTime(bankAccountPO.getLastUpdate());
        bankAccountTOType.setLastUpdate(cal);

        return bankAccountTOType;
    }

    /**
     * Convert a list of Transaction Persistence Object (PO) into a TransactionsTOType
     * i.e. the list of Transaction Transfer Objects (TO) represented by the XSD
     * @param transactionPOList
     * @return
     */
    private TransactionsTOType convertToTransactionsTOType(List<TransactionPO> transactionPOList){
        TransactionsTOType transactionsTOType = getTransactionsObjectFactory().createTransactionsTOType();
        for (TransactionPO transactionPO : transactionPOList) {
            TransactionTOType transactionTOType = convertToTransactionTOType(transactionPO);
            transactionsTOType.getTransactions().add(transactionTOType);
        }
        transactionsTOType.setTotalRecords(transactionsTOType.getTransactions().size());
        return transactionsTOType;
    }

    /**
     * Convert a single TransactionPO into a TransactionTOType
     * @param transactionPO
     * @return
     */
    private TransactionTOType convertToTransactionTOType(TransactionPO transactionPO) {
        TransactionTOType transactionTO = getTransactionsObjectFactory().createTransactionTOType();
        Calendar cal = Calendar.getInstance();

        transactionTO.setIBAN(transactionPO.getBankAccount().getIban());
        transactionTO.setDescription(transactionPO.getDescription());

        cal.setTime(transactionPO.getExecutionDate());
        transactionTO.setExecutionDate(cal);
        cal.setTime(transactionPO.getValueDate());
        transactionTO.setValueDate(cal);

        transactionTO.setCategory(transactionPO.getCategory());
        transactionTO.setSubCategory(transactionPO.getSubCategory());
        if (transactionPO.getAmount().compareTo(BigDecimal.ZERO) > 0) {
            transactionTO.setCredit(transactionPO.getAmount());
        }
        else {
            transactionTO.setDebit(transactionPO.getAmount());
        }

        transactionTO.setCurrency(transactionPO.getCurrency());

        cal.setTime(transactionPO.getCreatedDate());
        transactionTO.setCreateDate(cal);

        cal.setTime(transactionPO.getLastUpdate());
        transactionTO.setLastUpdate(cal);

        return transactionTO;
    }


}
