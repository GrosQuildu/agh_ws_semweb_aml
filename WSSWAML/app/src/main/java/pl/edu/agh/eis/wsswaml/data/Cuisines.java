package pl.edu.agh.eis.wsswaml.data;

import pl.edu.agh.eis.wsswaml.models.Cuisine;

public enum Cuisines {
    Arabian (4),
    American (1),
    Argentine (151),
    Armenian (175),
    Asian (2),
    Australian (201),
    Bakery (5),
    Balkans (341),
    Beverages (270),
    Burger (168),
    Cafe (30),
    Chinese (25),
    CoffeAndTea (161),
    Czech (671),
    Desserts (100),
    Donuts (959),
    DrinksOnly (268),
    European (38),
    FastFood (40),
    FingerFood (271),
    French (45),
    Georgian (205),
    Greek (156),
    Grill (181),
    HealthlyFood (143),
    IceCream (233),
    Indian (148),
    Italian (55),
    Kebab (178),
    Mexican (73),
    Pizza (82),
    Polish(219),
    Russian (84),
    Salad (998),
    Seafood (83),
    Spanish(89),
    Sushi(177),
    Tea(163),
    Turkish(142),
    Ukrainian (451),
    Vietnamese (99);

    private int value;
    private Cuisines(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }

}
