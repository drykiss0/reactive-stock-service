package com.globallogic.reactivestock.api.rest;

import com.globallogic.reactivestock.properties.GeneratorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/ticks")
public class TickController {

    private final GeneratorProperties generatorProperties;

    public TickController(GeneratorProperties properties) {
        this.generatorProperties = properties;
    }
}

/*
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account-owners/{owner}/accounts")
public class AccountController {

    private static final String OWNER = "owner";
    private static final String ID = "id";

    private final IdentityProvider identity;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("belongsToCustomer() && hasScope('accounts:write')")
    public AccountResponse createAccount(@PathVariable(OWNER) final String owner,
                                         @RequestBody @Valid final CreateAccountRequest request) {

        final var accountDetails = getOwner(owner)
            .createAccount(request.toCommand())
            .toAccountDetails();
        final var extraInformation = identity.isExtraInformationEnabled();

        return new AccountResponse(accountDetails, extraInformation);
    }

 */
