@API
Feature: Invoice API

  Scenario: Kullanıcı token alır
    Given API base URL "https://f9b9ffcb-f72d-4696-9adf-6c1319041437.mock.pstmn.io"
    When user sends POST request to "/token" with user "testUser" and pass "testPass"
    Then response status code should be 200
    And token should be present in response

  Scenario: Kullanıcı fatura görüntüler
    Given API base URL "https://f9b9ffcb-f72d-4696-9adf-6c1319041437.mock.pstmn.io"
    When user sends GET request to "/viewInvoice?barcode=123456"
    Then response should contain "invoice.pdf"

  Scenario: Kullanıcı fatura gönderir
    Given API base URL "https://f9b9ffcb-f72d-4696-9adf-6c1319041437.mock.pstmn.io"
    And token "abc123xyz" is used
    When user sends POST request to "/sendInvoice" with body:
      """
      {
        "Barcode": "123456"
      }
      """
    Then response should contain "Invoice sent successfully."
