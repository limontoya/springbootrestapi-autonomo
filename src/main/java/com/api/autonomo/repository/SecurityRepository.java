package com.api.autonomo.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface SecurityRepository {

	String findLoggedInOwnerEmail();

	void autoLogin(String ownerEmail, String ownerEmailKey);

}
