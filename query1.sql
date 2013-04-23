    select
        account0_.ID as ID0_0_,
        transactio1_.ID as ID2_1_,
        account0_.NUMBER as NUMBER0_0_,
        account0_.DATE_OF_BIRTH as DATE3_0_0_,
        account0_.email as email0_0_,
        account0_.name as name0_0_,
        account0_.MONTHLY_EMAIL_UPDATE as MONTHLY6_0_0_,
        account0_.REWARDS_NEWSLETTER as REWARDS7_0_0_,
        account0_.username as username0_0_,
        account0_1_.CREDIT_CARD as CREDIT1_1_0_,
        account0_1_.number as number1_0_,
        account0_1_.ACC_TYPE as ACC3_1_0_,
        transactio1_.NAME as NAME2_1_,
        transactio1_.NUMBER as NUMBER2_1_,
        transactio1_.AMOUNT as AMOUNT2_1_,
        transactio1_.CODE as CODE2_1_,
        transactio1_.ACCOUNT_ID as ACCOUNT6_0_0__,
        transactio1_.ID as ID0__ 
    from
        T_CUSTOMER account0_ 
    left outer join
        T_ACCOUNT account0_1_ 
            on account0_.ID=account0_1_.CUST_ID 
    left outer join
        T_ACCOUNT_TRANSACTION transactio1_ 
            on account0_.ID=transactio1_.ACCOUNT_ID 
    where
        account0_1_.number=?