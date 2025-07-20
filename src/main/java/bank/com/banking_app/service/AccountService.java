package bank.com.banking_app.service;

import bank.com.banking_app.dto.AccountDto;

import java.util.List;

public interface AccountService {

    AccountDto createAccount(AccountDto accountDto);

    AccountDto getAccountById(Long id);

    AccountDto deposit(Long id,Double amount);

    AccountDto withDraw(Long id,Double amount);

   List<AccountDto> getAllAccount();

   void deleteAccount(Long id);
}
