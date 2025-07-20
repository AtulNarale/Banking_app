package bank.com.banking_app.controller;

import bank.com.banking_app.dto.AccountDto;
import bank.com.banking_app.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // add account rest api
    @PostMapping
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto){
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    //
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable  Long id){
     AccountDto accountDto  = accountService.getAccountById(id);
        return  ResponseEntity.ok(accountDto);
    }

    //deposit rest api
    @PutMapping("/{id}/deposit")
    public  ResponseEntity<AccountDto> deposit(@PathVariable Long id,
                                               @RequestBody Map<String,Double>
                                               request){

      Double amount = request.get("amount");

      AccountDto accountDto =  accountService.deposit(id,amount);

      return  ResponseEntity.ok(accountDto);
    }

    //withdraw rest api

    @PutMapping("/{id}/withdraw")
    public  ResponseEntity<AccountDto> withDraw(@PathVariable Long id,
                                                @RequestBody Map<String,Double>
            request){
        Double amount = request.get("amount");
        AccountDto accountDto =  accountService.withDraw(id,amount);
        return  ResponseEntity.ok(accountDto);
    }

    //get all accounts rest api
    @GetMapping
    public  ResponseEntity<List<AccountDto>> getAllAccounts(){
       List<AccountDto> accounts = accountService.getAllAccount();
       return ResponseEntity.ok(accounts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable  Long id){
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Account deleted successfully");
    }

}
