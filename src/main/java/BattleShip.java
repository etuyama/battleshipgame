import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BattleShip {
    static List<String> battleshipNames = Arrays.asList("Stormbreaker", "Titan's Fury", "Shadow Vanguard", "Iron Leviathan", "Sea Phantom", "Eternal Crusader", "Vortex Reaver", "Neptune's Wrath", "Celestial Spear", "Thunderclap", "Obsidian Blade", "Solar Tempest", "Dread Horizon", "Lunar Sentinel", "Abyss Warden", "Inferno Scythe", "Aurora Striker", "Crimson Tide", "Void Marauder", "Echo Sabre", "Nightshade", "Ember Fang", "Blazing Tide", "Stellar Dominion", "Frost Reaver", "Phantom Gale", "Spectral Nova", "Steel Sovereign", "Sky Cleaver", "Tidal Colossus", "Iron Tempest", "Shadow Fortress", "Warp Reaver", "Radiant Leviathan", "Eclipse Fang", "Starbreaker", "Sovereign Flame", "Crimson Phantom", "Black Tempest", "Titan Forge", "Vortex Sabre", "Azure Titan", "Galactic Warden", "Oblivion's Edge", "Thunder Spear", "Void Tempest", "Celestial Leviathan", "Aether Striker", "Nebula Crusader", "Iron Warden");
    private String name;
    private List<String> coordinates;
    private static final Random rand = new Random();

    public String getName() {
        return name;
    }

    public BattleShip() {
        this.coordinates = new ArrayList<>(); // Garante inicialização
    }

    public void setName() {
        int index = rand.nextInt(battleshipNames.size());
        this.name = battleshipNames.get(index);
    }

    public List<String> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<String> coordinates) {
        this.coordinates = coordinates;
    }

    public void addCoordinate(String c) {
        this.coordinates.add(c);
    }

    public String getFirstCoordinate() {
        return this.coordinates.get(0);
    }

    public String getSecondCoordinate() {
        return this.coordinates.get(1);
    }

    public String getThirdCoordinate() {
        return this.coordinates.get(2);
    }
}
