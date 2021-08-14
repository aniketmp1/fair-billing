package com.edstem;


import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import javax.xml.bind.ValidationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.edstem.enums.SessionState;
import com.edstem.model.Session;
import com.edstem.model.User;
import com.edstem.service.BillCalculator;

@ExtendWith(MockitoExtension.class) 
class FairBillingApplicationTests {

	@InjectMocks
	BillCalculator calculator;
	
	@Test
	void givenValidInputLine_whenValidateAndAddToList_shouldRunWithoutException() throws ValidationException {
		calculator.validateAndAddToList("14:02:03 ALICE99 Start");
	}
	
	@Test()
	void givenInvalidTime_whenValidateAndAddToList_shouldThrowDateTimeParseException() throws ValidationException {
		 Assertions.assertThrows(DateTimeParseException.class, () -> {
			 calculator.validateAndAddToList("hh:mm:ss ALICE99 Start");
		  });
	}
	
	@Test()
	void givenInvalidLine_whenValidateAndAddToList_shouldThrowValidationException() throws ValidationException {
		 Assertions.assertThrows(ValidationException.class, () -> {
			 calculator.validateAndAddToList("");
		  });
	}
	
	@Test()
	void givenInvalidState_whenValidateAndAddToList_shouldThrowIllegalArgumentException() throws ValidationException {
		 Assertions.assertThrows(IllegalArgumentException.class, () -> {
			 calculator.validateAndAddToList("14:02:03 ALICE99 Strt");
		  });
	}
	
	@Test()
	void givenSessionData_whenValidateAndAddToList_shouldPerformHappyFlow() throws ValidationException {
//		Session session=new Session(LocalTime.parse("14:02:03"),"ALICE99",SessionState.Start);
		calculator.validateAndAddToList("14:02:03 ALICE99 Start");
		calculator.validateAndAddToList("14:02:13 ALICE99 End");
		calculator.processSession();
		User user=calculator.getUsers().iterator().next();
		Assertions.assertEquals(user.getNoOfSessions(), 1);
		Assertions.assertEquals(user.getTimeInSecs(), 10);
		Assertions.assertEquals(user.getUserId(), "ALICE99");
	}
	
	@Test()
	void givenSessionData_whenprocessSession_shouldHandleEndEdgeCase() throws ValidationException {
//		Session session=new Session(LocalTime.parse("14:02:03"),"ALICE99",SessionState.Start);
		calculator.validateAndAddToList("14:02:03 ALICE99 Start");
		calculator.validateAndAddToList("14:02:05 CHARLIE End");
		calculator.validateAndAddToList("14:02:34 ALICE99 End");
		calculator.processSession();
		calculator.getUsers().forEach(user -> 
		{
			if(user.getUserId().equals("CHARLIE"))
			{
				Assertions.assertEquals(user.getNoOfSessions(), 1);
				Assertions.assertEquals(user.getTimeInSecs(), 2);
			}
			
		});
	}
	
	@Test()
	void givenSessionData_whenprocessSession_shouldHandleStartEdgeCase() throws ValidationException {
//		Session session=new Session(LocalTime.parse("14:02:03"),"ALICE99",SessionState.Start);
		calculator.validateAndAddToList("14:02:03 ALICE99 Start");
		calculator.validateAndAddToList("14:02:05 CHARLIE Start");
		calculator.validateAndAddToList("14:02:34 ALICE99 End");
		calculator.processSession();
		calculator.getUsers().forEach(user -> 
		{
			if(user.getUserId().equals("CHARLIE"))
			{
				Assertions.assertEquals(user.getNoOfSessions(), 1);
				Assertions.assertEquals(user.getTimeInSecs(), 29);
			}
			
		});
	}
	
	
	

}
