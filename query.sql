    select
        account0_.ID as ID0_,
        account0_.NUMBER as NUMBER0_,
        account0_.DATE_OF_BIRTH as DATE3_0_,
        account0_.email as email0_,
        account0_.name as name0_,
        account0_.MONTHLY_EMAIL_UPDATE as MONTHLY6_0_,
        account0_.REWARDS_NEWSLETTER as REWARDS7_0_,
        account0_.username as username0_,
        account0_1_.CREDIT_CARD as CREDIT1_1_,
        account0_1_.number as number1_,
        account0_1_.ACC_TYPE as ACC3_1_ 
    from
        T_CUSTOMER account0_ 
    join
        T_ACCOUNT account0_1_ 
            on account0_.ID=account0_1_.CUST_ID 
    where
        account0_.username=? 
    order by
        account0_1_.number