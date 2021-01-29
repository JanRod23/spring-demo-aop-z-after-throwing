package com.luv2code.aopdemo.aspect;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.luv2code.aopdemo.Account;

@Aspect
@Component
@Order(2)
public class MyDemoLoggingAspect {
	
	// Add a new advice for @AfterReturning on the findAccounts method
	@AfterReturning(
			pointcut="execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))",
			returning="result")
	public void afterReturningFindAccountsAdvice(JoinPoint theJoinPoint, List<Account> result) {
		
		// Print out which method is being advised on
		String method = theJoinPoint.getSignature().toShortString();
		System.out.println("\n=====>> Executing @AtferRturning on method: " + method);
		
		// Print out the results of the method call
		System.out.println("\n=====>> result is: " + result);
		
		// Convert the account names to all UPPER-CASE
		convertAccountNamesToUpperCase(result);
		System.out.println("\n======>> result is: " + result);
	}
	
	private void convertAccountNamesToUpperCase(List<Account> result) {
		// Loop through accounts and change names to upper-case
		for(Account tempAccount : result) {
			String upperName = tempAccount.getName().toUpperCase();
			tempAccount.setName(upperName);
		}
	}

	@Before("com.luv2code.aopdemo.aspect.LuvAopExpressions.forDaoPackageNoGetterSetter()")
	public void beforeAddAccountAdvice(JoinPoint theJoinPoint) {
		System.out.println("\n==========>> Executing @Before advice on method");
		
		// Display the method signature
		MethodSignature methodSig = (MethodSignature) theJoinPoint.getSignature();
		System.out.println("Method: " + methodSig);
		
		// Display the method arguments
		
		
		//Get arguments
		Object[] args = theJoinPoint.getArgs();
		
		// Loop trough arguments
		for (Object tempArg : args) {
			System.out.println("Argument: " + tempArg);
			
			if(tempArg instanceof Account) {
				// Cast and print Account specific stuff
				Account theAccount = (Account) tempArg;
				System.out.println("Account name: " + theAccount.getName());
				System.out.println("Account level: " + theAccount.getLevel());
			}
		}
	}
}
