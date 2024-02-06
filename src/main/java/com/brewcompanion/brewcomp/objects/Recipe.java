package com.brewcompanion.brewcomp.objects;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import lombok.Data;

@Data
public class Recipe implements Serializable{
    private static Random random;

    private final String name;
    private final String hash;
    private final int id;

    private final String author;
    private final String authorHash;
    private final int authorID;


    private String description;
    private String recipeInstructions;

    private String parentName;
    private String parentHash;
    private  int parentID;
    private String parentAuthor;
    private String parentAutherHash;
    private int parentAuthorID;

    

    private String recipeType;

    public Recipe(String name, String hash, int id, String author, String authorHash, int authorID, String description) {
        this.name = name;
        this.hash = hash;
        this.id = id;
        this.author = author;
        this.authorHash = authorHash;
        this.authorID = authorID;
        this.description = description;
    }
        

    public static List<Recipe> getExampleData(){
        List<Recipe> exampleData = new ArrayList<>();

        // Recipe 1
        Recipe recipe1 = new Recipe(
            "Chocolate Stout",
            "hash1",
            1,
            "John Doe",
            "authorHash1",
            1,
            "A rich and chocolatey stout with a smooth finish."
        );
        recipe1.setParentName("Stout");
        recipe1.setParentHash("parentHash1");
        recipe1.setParentID(1);
        recipe1.setParentAuthor("Jane Smith");
        recipe1.setParentAutherHash("parentAuthorHash1");
        recipe1.setParentAuthorID(1);
        recipe1.setRecipeInstructions("1. Boil water\n2. Add malt\n3. Add hops\n4. Ferment\n5. Add cocoa powder\n6. Bottle");
        recipe1.setRecipeType("Stout");
        exampleData.add(recipe1);

        // Recipe 2
        Recipe recipe2 = new Recipe(
            "IPA",
            "hash2",
            2,
            "Jane Smith",
            "authorHash2",
            1,
            "A hoppy and refreshing IPA with a citrusy aroma."
        );
        recipe2.setParentName("Pale Ale");
        recipe2.setParentHash("parentHash2");
        recipe2.setParentID(1);
        recipe2.setParentAuthor("John Doe");
        recipe2.setParentAutherHash("parentAuthorHash2");
        recipe2.setParentAuthorID(1);
        recipe2.setRecipeInstructions("1. Boil water\n2. Add malt\n3. Add hops\n4. Ferment\n5. Bottle");
        recipe2.setRecipeType("IPA");
        exampleData.add(recipe2);

        // Recipe 3
        Recipe recipe3 = new Recipe(
            "Belgian Dubbel",
            "hash3",
            3,
            "Bob Johnson",
            "authorHash3",
            1,
            "A malty and complex Belgian Dubbel with notes of dark fruit."
        );
        recipe3.setParentName("Belgian Ale");
        recipe3.setParentHash("parentHash3");
        recipe3.setParentID(1);
        recipe3.setParentAuthor("Bob Johnson");
        recipe3.setParentAutherHash("parentAuthorHash3");
        recipe3.setParentAuthorID(1);
        recipe3.setRecipeInstructions("1. Boil water\n2. Add malt\n3. Add hops\n4. Ferment\n5. Add dark candi sugar\n6. Bottle");
        recipe3.setRecipeType("Belgian Dubbel");
        exampleData.add(recipe3);

        return exampleData;
    }
    public static List<Recipe> getRandomData(int count) {
            List<Recipe> randomData = new ArrayList<>();
            if(random == null){
                random = new Random();
            }

            for (int i = 0; i < count; i++) {
                String name = "Recipe " + (i + 1);
                String hash = "hash" + (i + 1);
                Integer id = i;
                String author = "Author " + (i + 1);
                String authorHash = "authorHash" + (i + 1);
                int authorID = (i + 1);
                String description = "Description for Recipe " + (i + 1);
                String parentName = "Parent Recipe " + random.nextInt(count);
                String parentHash = "parentHash" + random.nextInt(count);
                int parentID = random.nextInt(count);
                String parentAuthor = "Parent Author " + random.nextInt(count);
                String parentAuthorHash = "parentAuthorHash" + random.nextInt(count);
                int parentAuthorID = random.nextInt(count);
                String recipeInstructions = "1. Boil water\n2. Add malt\n3. Add hops\n4. Ferment\n5. Bottle";
                String recipeType = "Recipe Type " + random.nextInt(count);

                Recipe recipe = new Recipe(name, hash, id, author, authorHash, authorID, description);
                recipe.setParentName(parentName);
                recipe.setParentHash(parentHash);
                recipe.setParentID(parentID);
                recipe.setParentAuthor(parentAuthor);
                recipe.setParentAutherHash(parentAuthorHash);
                recipe.setParentAuthorID(parentAuthorID);
                recipe.setRecipeInstructions(recipeInstructions);
                recipe.setRecipeType(recipeType);

                randomData.add(recipe);
            }

            return randomData;
        }
}
