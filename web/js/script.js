/**
 * Created by nlee on 2019-04-17.
 */
function navigateToCreate() {
    var project = prompt("Enter Passcode to create customer: ");
    var myElement = document.getElementById("project_name").innerText;
    if (myElement == project) {
        document.getElementById("delete").submit();
        alert("Project:" + myElement + " has been deleted.");
    } else {
        alert("Cancelled");
    }
}