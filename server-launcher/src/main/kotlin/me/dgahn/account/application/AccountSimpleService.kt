package me.dgahn.account.application

class AccountSimpleService {
//    fun signUp(param: SignUpParam): Account {
//        val accountRepo: AccountRepository = injector().get()
//        return use(accountRepo) {
//            val findAccount = runCatching { accountRepo.findOne(param.email) }.getOrNull()
//            if (findAccount != null) {
//                throw EmailDuplicationException()
//            }
//            val result = accountRepo.save(
//                Account(
//                    email = param.email,
//                    name = param.name,
//                    password = param.password,
//                    role = param.role,
//                    createdDate = TimeProvider.nowForSql()
//                ).encryptPassword()
//            )
//            result
//        }
//    }
//
//    fun signIn(param: SignInParam): Account {
//        val accountRepo: AccountRepository = injector().get()
//        return use(accountRepo) {
//            val findAccount = accountRepo.findOne(param.email)
//            if (!findAccount.checkPassword(param.password)) {
//                throw PasswordInvalidException()
//            }
//
//            findAccount
//        }
//    }
//
//    fun update(param: UpdateParam): Account {
//        val accountRepo: AccountRepository = injector().get()
//        return use(accountRepo) {
//            val findAccount = runCatching { accountRepo.findOne(param.email) }
//                .getOrElse { throw AccountNotFoundException() }
//            accountRepo.update(findAccount.update(param))
//        }
//    }
//
//    fun getAllAccount(param: GetAllAccountPram): List<Account> {
//        val accountRepo: AccountRepository = injector().get()
//        return use(accountRepo) { accountRepo.findBy(param.roleList) }
//    }
}
