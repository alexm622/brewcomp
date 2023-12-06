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
    private final String id;

    private final String author;
    private final String authorHash;
    private final String authorID;

    private String parentName;
    private String parentHash;
    private String parentID;
    private String parentAuthor;
    private String parentAutherHash;
    private String parentAuthorID;

    private final String description;

    private String recipeInstructions;
    

    private String recipeType;

    public Recipe(String name, String hash, String id, String author, String authorHash, String authorID, String description) {
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
            "id1",
            "John Doe",
            "authorHash1",
            "authorID1",
            "A rich and chocolatey stout with a smooth finish."
        );
        recipe1.setParentName("Stout");
        recipe1.setParentHash("parentHash1");
        recipe1.setParentID("parentId1");
        recipe1.setParentAuthor("Jane Smith");
        recipe1.setParentAutherHash("parentAuthorHash1");
        recipe1.setParentAuthorID("parentAuthorID1");
        recipe1.setRecipeInstructions("1. Boil water\n2. Add malt\n3. Add hops\n4. Ferment\n5. Add cocoa powder\n6. Bottle");
        recipe1.setRecipeType("Stout");
        exampleData.add(recipe1);

        // Recipe 2
        Recipe recipe2 = new Recipe(
            "IPA",
            "hash2",
            "id2",
            "Jane Smith",
            "authorHash2",
            "authorID2",
            "A hoppy and refreshing IPA with a citrusy aroma."
        );
        recipe2.setParentName("Pale Ale");
        recipe2.setParentHash("parentHash2");
        recipe2.setParentID("parentId2");
        recipe2.setParentAuthor("John Doe");
        recipe2.setParentAutherHash("parentAuthorHash2");
        recipe2.setParentAuthorID("parentAuthorID2");
        recipe2.setRecipeInstructions("1. Boil water\n2. Add malt\n3. Add hops\n4. Ferment\n5. Bottle");
        recipe2.setRecipeType("IPA");
        exampleData.add(recipe2);

        // Recipe 3
        Recipe recipe3 = new Recipe(
            "Belgian Dubbel",
            "hash3",
            "id3",
            "Bob Johnson",
            "authorHash3",
            "authorID3",
            "A malty and complex Belgian Dubbel with notes of dark fruit."
        );
        recipe3.setParentName("Belgian Ale");
        recipe3.setParentHash("parentHash3");
        recipe3.setParentID("parentId3");
        recipe3.setParentAuthor("Bob Johnson");
        recipe3.setParentAutherHash("parentAuthorHash3");
        recipe3.setParentAuthorID("parentAuthorID3");
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
                String id = "id" + (i + 1);
                String author = "Author " + (i + 1);
                String authorHash = "authorHash" + (i + 1);
                String authorID = "authorID" + (i + 1);
                String description = "Description for Recipe " + (i + 1);
                String parentName = "Parent Recipe " + random.nextInt(count);
                String parentHash = "parentHash" + random.nextInt(count);
                String parentID = "parentId" + random.nextInt(count);
                String parentAuthor = "Parent Author " + random.nextInt(count);
                String parentAutherHash = "parentAuthorHash" + random.nextInt(count);
                String parentAuthorID = "parentAuthorID" + random.nextInt(count);
                String recipeInstructions = "1. Boil water\n2. Add malt\n3. Add hops\n4. Ferment\n5. Bottle";
                String recipeType = "Recipe Type " + random.nextInt(count);

                Recipe recipe = new Recipe(name, hash, id, author, authorHash, authorID, description);
                recipe.setParentName(parentName);
                recipe.setParentHash(parentHash);
                recipe.setParentID(parentID);
                recipe.setParentAuthor(parentAuthor);
                recipe.setParentAutherHash(parentAutherHash);
                recipe.setParentAuthorID(parentAuthorID);
                recipe.setRecipeInstructions(recipeInstructions);
                recipe.setRecipeType(recipeType);

                randomData.add(recipe);
            }

            return randomData;
        }
}
