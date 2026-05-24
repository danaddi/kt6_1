package com.example.kt6_4.data.mock


import com.example.kt6_4.domain.model.Laureate
import com.example.kt6_4.domain.model.NobelPrize
import com.example.kt6_4.domain.model.PrizeCategories

object MockData {

    private val laureates2023 = listOf(
        // Physics 2023
        Laureate(
            id = "1",
            firstName = "Pierre",
            lastName = "Agostini",
            motivation = "\"for experimental methods that generate attosecond pulses of light for the study of electron dynamics in matter\"",
            share = "3"
        ),
        Laureate(
            id = "2",
            firstName = "Ferenc",
            lastName = "Krausz",
            motivation = "\"for experimental methods that generate attosecond pulses of light for the study of electron dynamics in matter\"",
            share = "3"
        ),
        Laureate(
            id = "3",
            firstName = "Anne",
            lastName = "L'Huillier",
            motivation = "\"for experimental methods that generate attosecond pulses of light for the study of electron dynamics in matter\"",
            share = "3"
        ),
        // Chemistry 2023
        Laureate(
            id = "4",
            firstName = "Moungi",
            lastName = "Bawendi",
            motivation = "\"for the discovery and synthesis of quantum dots\"",
            share = "3"
        ),
        Laureate(
            id = "5",
            firstName = "Louis",
            lastName = "Brus",
            motivation = "\"for the discovery and synthesis of quantum dots\"",
            share = "3"
        ),
        Laureate(
            id = "6",
            firstName = "Alexei",
            lastName = "Ekimov",
            motivation = "\"for the discovery and synthesis of quantum dots\"",
            share = "3"
        ),
        // Medicine 2023
        Laureate(
            id = "7",
            firstName = "Katalin",
            lastName = "Karikó",
            motivation = "\"for their discoveries concerning nucleoside base modifications that enabled the development of effective mRNA vaccines against COVID-19\"",
            share = "2"
        ),
        Laureate(
            id = "8",
            firstName = "Drew",
            lastName = "Weissman",
            motivation = "\"for their discoveries concerning nucleoside base modifications that enabled the development of effective mRNA vaccines against COVID-19\"",
            share = "2"
        ),
        // Literature 2023
        Laureate(
            id = "9",
            firstName = "Jon",
            lastName = "Fosse",
            motivation = "\"for his innovative plays and prose which give voice to the unsayable\"",
            share = "1"
        ),
        // Peace 2023
        Laureate(
            id = "10",
            firstName = "Narges",
            lastName = "Mohammadi",
            motivation = "\"for her fight against the oppression of women in Iran and her fight to promote human rights and freedom for all\"",
            share = "1"
        ),
        // Economics 2023
        Laureate(
            id = "11",
            firstName = "Claudia",
            lastName = "Goldin",
            motivation = "\"for having advanced our understanding of women's labour market outcomes\"",
            share = "1"
        )
    )

    private val laureates2024 = listOf(
        // Physics 2024
        Laureate(
            id = "12",
            firstName = "John",
            lastName = "Hopfield",
            motivation = "\"for foundational discoveries and inventions that enable machine learning with artificial neural networks\"",
            share = "2"
        ),
        Laureate(
            id = "13",
            firstName = "Geoffrey",
            lastName = "Hinton",
            motivation = "\"for foundational discoveries and inventions that enable machine learning with artificial neural networks\"",
            share = "2"
        ),
        // Chemistry 2024
        Laureate(
            id = "14",
            firstName = "David",
            lastName = "Baker",
            motivation = "\"for computational protein design\"",
            share = "2"
        ),
        Laureate(
            id = "15",
            firstName = "Demis",
            lastName = "Hassabis",
            motivation = "\"for protein structure prediction\"",
            share = "4"
        ),
        Laureate(
            id = "16",
            firstName = "John",
            lastName = "Jumper",
            motivation = "\"for protein structure prediction\"",
            share = "4"
        ),
        // Literature 2024
        Laureate(
            id = "17",
            firstName = "Han",
            lastName = "Kang",
            motivation = "\"for her intense poetic prose that confronts historical traumas and exposes the fragility of human life\"",
            share = "1"
        ),
        // Peace 2024
        Laureate(
            id = "18",
            firstName = "Nihon",
            lastName = "Hidankyo",
            motivation = "\"for its efforts to achieve a world free of nuclear weapons and for demonstrating through witness testimony that nuclear weapons must never be used again\"",
            share = "1"
        )
    )

    fun getNobelPrizes(): List<NobelPrize> {
        return listOf(
            NobelPrize(
                year = "2024",
                category = PrizeCategories.PHYSICS,
                overallMotivation = "\"for foundational discoveries and inventions that enable machine learning with artificial neural networks\"",
                laureates = laureates2024.filter { it.id in listOf("12", "13") }
            ),
            NobelPrize(
                year = "2024",
                category = PrizeCategories.CHEMISTRY,
                overallMotivation = "\"for computational protein design and protein structure prediction\"",
                laureates = laureates2024.filter { it.id in listOf("14", "15", "16") }
            ),
            NobelPrize(
                year = "2024",
                category = PrizeCategories.LITERATURE,
                overallMotivation = "",
                laureates = laureates2024.filter { it.id == "17" }
            ),
            NobelPrize(
                year = "2024",
                category = PrizeCategories.PEACE,
                overallMotivation = "",
                laureates = laureates2024.filter { it.id == "18" }
            ),
            NobelPrize(
                year = "2023",
                category = PrizeCategories.PHYSICS,
                overallMotivation = "\"for experimental methods that generate attosecond pulses of light for the study of electron dynamics in matter\"",
                laureates = laureates2023.filter { it.id in listOf("1", "2", "3") }
            ),
            NobelPrize(
                year = "2023",
                category = PrizeCategories.CHEMISTRY,
                overallMotivation = "\"for the discovery and synthesis of quantum dots\"",
                laureates = laureates2023.filter { it.id in listOf("4", "5", "6") }
            ),
            NobelPrize(
                year = "2023",
                category = PrizeCategories.MEDICINE,
                overallMotivation = "\"for their discoveries concerning nucleoside base modifications that enabled the development of effective mRNA vaccines against COVID-19\"",
                laureates = laureates2023.filter { it.id in listOf("7", "8") }
            ),
            NobelPrize(
                year = "2023",
                category = PrizeCategories.LITERATURE,
                overallMotivation = "",
                laureates = laureates2023.filter { it.id == "9" }
            ),
            NobelPrize(
                year = "2023",
                category = PrizeCategories.PEACE,
                overallMotivation = "",
                laureates = laureates2023.filter { it.id == "10" }
            ),
            NobelPrize(
                year = "2023",
                category = PrizeCategories.ECONOMICS,
                overallMotivation = "",
                laureates = laureates2023.filter { it.id == "11" }
            )
        )
    }
}