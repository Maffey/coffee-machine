package machine

import java.util.Scanner

class CoffeeMachine(var status: Status = Status.DEFAULT_STATE, var water: Int = 400, var milk: Int = 540,
                    var coffeeBeans: Int = 120, var cups: Int = 9, var money: Int = 550) {

    companion object {
        enum class Status() {
            DEFAULT_STATE,
            PURCHASE_ACTION,
            FILL_WATER,
            FILL_MILK,
            FILL_COFFEE_BEANS,
            FILL_CUPS,
            TAKE_ACTION,
            SHOW_INVENTORY,
            OFFLINE;
        }
    }

    fun promptManager(scanner: Scanner) {
        when (status) {
            Status.DEFAULT_STATE -> {
                println("Write action (buy, fill, take, remaining, exit): > ")
                actionManager(scanner.next())
            }
            Status.PURCHASE_ACTION -> {
                println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: > ")
                actionManager(scanner.next())
            }
            Status.FILL_WATER -> {
                println("Write how many ml of water do you want to add: > ")
                actionManager(scanner.next())
            }
            Status.FILL_MILK -> {
                println("Write how many ml of milk do you want to add: > ")
                actionManager(scanner.next())
            }
            Status.FILL_COFFEE_BEANS -> {
                println("Write how many grams of coffee beans do you want to add: > ")
                actionManager(scanner.next())
            }
            Status.FILL_CUPS -> {
                println("Write how many disposable cups of coffee do you want to add: > ")
                actionManager(scanner.next())
            }
            Status.TAKE_ACTION -> {
                takeAction()
                status = Status.DEFAULT_STATE
            }
            Status.SHOW_INVENTORY -> {
                printCoffeeMachineInventory()
                status = Status.DEFAULT_STATE
            }
        }
    }

    private fun actionManager(userInput: String) {
        when (status) {
            Status.DEFAULT_STATE -> {
                status = when (userInput) {
                    "buy" -> Status.PURCHASE_ACTION
                    "fill" -> Status.FILL_WATER
                    "take" -> Status.TAKE_ACTION
                    "remaining" -> Status.SHOW_INVENTORY
                    "exit" -> Status.OFFLINE
                    else -> Status.DEFAULT_STATE
                }
            }
            Status.PURCHASE_ACTION -> {
                selectCoffee(userInput)
                status = Status.DEFAULT_STATE
            }
            Status.FILL_WATER -> {
                water += userInput.toInt()
                status = Status.FILL_MILK
            }
            Status.FILL_MILK -> {
                milk += userInput.toInt()
                status = Status.FILL_COFFEE_BEANS
            }
            Status.FILL_COFFEE_BEANS -> {
                coffeeBeans += userInput.toInt()
                status = Status.FILL_CUPS
            }
            Status.FILL_CUPS -> {
                cups += userInput.toInt()
                status = Status.DEFAULT_STATE
            }
        }
    }


    private fun printCoffeeMachineInventory() {
        println()
        println("The coffee machine has:")
        println("$water of water")
        println("$milk of milk")
        println("$coffeeBeans of coffee beans")
        println("$cups of disposable cups")
        println("$money of money")
        println()
    }

    private fun selectCoffee(userInput: String) {
        when (userInput) {
            "1" -> {
                purchaseAttempt(250, 0, 16, 4)
            }
            "2" -> {
                purchaseAttempt(350, 75, 20, 7)
            }
            "3" -> {
                purchaseAttempt(200, 100, 12, 6)
            }
            "back" -> Unit
        }
    }

    private fun purchaseAttempt(waterCost: Int, milkCost: Int, coffeeBeansCost: Int, price: Int) {
        var isPossibleToMake = true

        if (cups == 0) {
            println("Sorry, not enough cups!\n")
            isPossibleToMake = false
        }
        if (water < waterCost) {
            println("Sorry, not enough water!\n")
            isPossibleToMake = false
        }
        if (milk < milkCost) {
            println("Sorry, not enough milk!\n")
            isPossibleToMake = false
        }
        if (coffeeBeans < coffeeBeansCost) {
            println("Sorry, not enough coffee beans!\n")
            isPossibleToMake = false
        }

        if (isPossibleToMake) {
            println("I have enough resources, making you a coffee!\n")
            water -= waterCost
            milk -= milkCost
            coffeeBeans -= coffeeBeansCost
            money += price
            cups -= 1
        }
    }

    private fun takeAction() {
        println("I gave you $$money\n")
        money = 0
    }

}

fun main() {
    val scanner = Scanner(System.`in`)
    val coffeeMachine = CoffeeMachine()

    while (coffeeMachine.status != CoffeeMachine.Companion.Status.OFFLINE) {
        coffeeMachine.promptManager(scanner)
    }
}
