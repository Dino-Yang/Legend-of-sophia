public class potion {
    int count;
    int healAmount;
    String naam;

    public potion(int count, int healAmount, String naam) {
        this.count = count;
        this.healAmount = healAmount;
        this.naam = naam;
    }

    public potion() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getHealAmount() {
        return healAmount;
    }

    public void setHealAmount(int healAmount) {
        this.healAmount = healAmount;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }
}
