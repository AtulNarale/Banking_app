package bank.com.banking_app.service;

import bank.com.banking_app.dto.AccountDto;
import bank.com.banking_app.entity.Account;

import bank.com.banking_app.repository.AccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    public AccountServiceImpl(AccountRepository accountRepository, ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = modelMapper.map(accountDto, Account.class);
        Account savedAccount = accountRepository.save(account);
        return modelMapper.map(savedAccount, AccountDto.class);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id"));
        return modelMapper.map(account, AccountDto.class);
    }

    @Override
    public AccountDto deposit(Long id, Double amount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account id does not exist"));
        account.setBalance(account.getBalance() + amount);
        return modelMapper.map(accountRepository.save(account), AccountDto.class);
    }

    @Override
    public AccountDto withDraw(Long id, Double amount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account id does not exist"));
        if (account.getBalance() < amount) throw new RuntimeException("Insufficient balance");
        account.setBalance(account.getBalance() - amount);
        return modelMapper.map(accountRepository.save(account), AccountDto.class);
    }

    @Override
    public List<AccountDto> getAllAccount() {
        return accountRepository.findAll()
                .stream()
                .map(account -> modelMapper.map(account, AccountDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account id does not exist"));
        accountRepository.deleteById(id);
    }
}

