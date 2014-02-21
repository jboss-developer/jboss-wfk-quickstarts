package org.jboss.as.quickstarts.kitchensink.test.page;

import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.as.quickstarts.kitchensink.test.Member;
import org.openqa.selenium.WebElement;

public class MemberPageFragment {

    @FindByJQuery("td:nth-child(2)")
    private WebElement name;

    @FindByJQuery("td:nth-child(3)")
    private WebElement email;

    @FindByJQuery("td:nth-child(4)")
    private WebElement phoneNumber;

    public Member getMember() {
        return new Member(name.getText(), email.getText(), phoneNumber.getText());
    }
}
