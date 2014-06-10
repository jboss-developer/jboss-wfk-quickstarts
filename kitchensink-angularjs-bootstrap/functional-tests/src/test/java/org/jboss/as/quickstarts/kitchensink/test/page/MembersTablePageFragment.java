package org.jboss.as.quickstarts.kitchensink.test.page;

import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.graphene.fragment.Root;
import org.jboss.as.quickstarts.kitchensink.test.Member;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.jboss.arquillian.graphene.Graphene.waitModel;

public class MembersTablePageFragment {
    /**
     * Locator for rows of the members table
     */
    @Root
    private WebElement membersTable;

    /**
     * Locator for rows of the members table
     */
    @FindByJQuery("tbody tr.ng-scope")
    private List<MemberPageFragment> members;


    public void waitForNewMember(Member member) {
        waitModel().until().element(membersTable).text().contains(member.getName());
    }

    public Member getLatestMember() {
        waitUntilPresent();
        return members.get(0).getMember();
    }

    public int getMemberCount() {
        waitUntilPresent();
        return members.size();
    }

    public void waitUntilPresent() {
        waitModel().until().element(membersTable).is().visible();
    }

}
