package com.ymc.todos.pages

/**
 * Created by ymc on 12/12/2016.
 */
import geb.Page
import org.openqa.selenium.JavascriptExecutor

class TodosPage extends Page {
    static url = "http://todomvc.com/examples/angular2/"

    static at = {
        waitFor (5,0.5) {
            $("h1").text() == "todos"
        }
    }

    static content = {
        selectAllCheckboxes { $("input", class:"toggle-all")}
        inputField { $("input", 0) }
        todoItemList { $("ul", class:"todo-list)") }
        todoItems (required:false) {modulelist TodoItemModal, todoItemList.find("li")}
        itemNum { $("span", class:"todo-count").find("strong") }
        clearCompletedButton (required:false){ $("button", class:"clear-completed")}
    }

    def void typeTextToInput(String input) {
        inputField.value(input + '\r')
    }

    def void addOneItem(String label){
        typeTextToInput(label)
    }

    def int getTodosNum() {
       int num = 0
        JavascriptExecutor jse = (JavascriptExecutor) driver
        jse.executeScript("scroll(0, 250)")
        todoItems.find{todo -> todo.has("div", class:"view")}
        num = todoItems.size()
        return num
    }

    def String getItemNum(){
        return itemNum.text()
    }

    def TodoItemModal getItemByRowNum(int rowNum){
        todoItems[rowNum]
    }

    def TodoItemModal getItemByLabel(String label){
        todoItems.find{ item -> item.has("label", text: iContains(label))}
    }

    def editItemByRowNum(int rowNum, String newLabel){
        TodoItemModal item = getItemByRowNum(rowNum)
        interact {
            doubleClick(item)
            item.value(newLabel)
        }
    }

    def editItemByLabel(String oldLabel, String newLabel){
        TodoItemModal item = getItemByLabel(oldLabel)
        interact {
            doubleClick(item)
            item.value(newLabel)
        }
    }

    def boolean clearCompleted(){
        boolean allClean = false
        selectAllCheckboxes.click()
        clearCompletedButton.click()
        if (getItemNum()=='0'){ allClean = true}
        return allClean
    }
}
