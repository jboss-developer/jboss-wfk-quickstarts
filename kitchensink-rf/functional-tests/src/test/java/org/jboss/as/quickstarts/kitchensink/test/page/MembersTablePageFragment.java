/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.kitchensink.test.page;

import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.as.quickstarts.kitchensink.test.Member;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.jboss.arquillian.graphene.Graphene.waitModel;

public class MembersTablePageFragment {
    /**
     * Locator for rows of the members table
     */
    @FindByJQuery("[id*='memberList'] table tbody[id*='tb']")
    private WebElement table;

    /**
     * Locator for rows of the members table
     */
    @FindByJQuery("[id*='memberList'] table tbody[id*='tb'] tr")
    private List<MemberPageFragment> members;


    public void waitForNewMember(Member member) {
        waitModel().until().element(table).text().contains(member.getName());
    }

    public Member getLatestMember() {
        return members.get(0).getMember();
    }

    public int getMemberCount() {
        return members.size();
    }

}
