package com.ylp.date;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.ylp.date.app.TestContextInitor;
import com.ylp.date.mgr.TestRelation;
import com.ylp.date.mgr.TestUser;

@RunWith(Suite.class)
@SuiteClasses({ TestUser.class, TestRelation.class })
public class DateTestSuite {

}
