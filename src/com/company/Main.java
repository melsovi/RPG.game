package com.company;

import java.util.Random;

public class Main {

    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence = " ";
    public static int[] heroesHealth = {270, 260, 250, 200, 300, 230, 280, 225};
    public static int[] heroesDamages = {15, 20, 25, 0, 5, 12, 20, 0};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "Lucky", "Berserk", "Thor"};
    public static int round_number = 0;

    public static void main(String[] args) {
        printStatistic();
        while (!isGAmeFinished()) {
            round();
        }
    }

    public static boolean isMedicHeal() {
        boolean healing = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[3] - bossDamage <= 0) {
                heroesHealth[3] = 0;
                healing = false;
                break;
            } else if (heroesHealth[i] < 100 && heroesHealth[i] > 0) {
                healing = true;
                heroesHealth[i] = heroesHealth[i] + generateRandomNumber();
                Random random = new Random();
                int name = random.nextInt(heroesAttackType.length);
                System.out.println("Medic вылечил: " + heroesAttackType[name] + " на " + generateRandomNumber()
                        + " единиц жизни.");
                break;
            } else if (heroesHealth[i] > 100 && heroesHealth[i] > 0) {
                healing = false;
            } else {
                healing = false;
            }

        }
        return healing;
    }

    public static int generateRandomNumber() {
        Random random = new Random();
        int number = random.nextInt(150);
        return number;
    }

    public static void changeBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length);
        bossDefence = heroesAttackType[randomIndex];
        System.out.println("Boss chose " + bossDefence);
    }

    public static void round() {
        round_number++;
        changeBossDefence();
        isMedicHeal();
        if (bossHealth > 0){
            bossHits();
        }
        heroesHit();
        printStatistic();
        golemHelp();
        lucky();
        talentOfBerserk();
        thor();
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamages.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coeff = random.nextInt(9 + 2);
                    if (bossHealth - heroesDamages[i] * coeff < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamages[i] * coeff;
                    }
                    System.out.println("Critical Damage " + heroesDamages[i] * coeff);
                } else {
                    if (bossHealth - heroesDamages[i] < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamages[i];
                    }
                }
            }
        }
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static boolean isGAmeFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss Won!!!!");
        }
        return allHeroesDead;
    }

    public static void printStatistic() {
        System.out.println(round_number + " round __________________________");
        System.out.println("Boss Health:" + bossHealth + " (" + bossDamage + ")");
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health:" + heroesHealth[i] + " (" + heroesDamages[i] + ") ");
        }
        System.out.println("____________________________");
    }

    public static boolean golemHelp() {
        boolean help = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[4] - bossDamage <= 0) {
                heroesHealth[4] = 0;
                help = false;
                break;
            } else if (heroesHealth[4] <= 0 && heroesHealth[i] <= 0) {
                help = false;
                break;
            } else if (heroesHealth[4] - (bossDamage / 5) > 0 && heroesHealth[i] < 0) {
                help = false;
                break;
            } else if (heroesHealth[4] > 0) {
                heroesHealth[4] = heroesHealth[4] - bossDamage / 5;
                heroesHealth[i] = heroesHealth[i] + bossDamage / 5;
                help = true;
                break;
            } else {
                help = false;
                break;
            }
        }
        return help;
    }

    public static void lucky() {
        Random random = new Random();
        int reply = random.nextInt(2);
        if (reply == 0) {
            System.out.println(heroesAttackType[5] + " не получилось уклониться!");
        }
        if (reply == 1) {
            System.out.println(heroesAttackType[5] + " удачно уклонился!");
            heroesHealth[5] = heroesHealth[5] + bossDamage - (bossDamage / 5);
        }
    }

    public static void talentOfBerserk() {
        Random random = new Random();
        int reply = random.nextInt(2);
        if (reply == 0) {
            System.out.println(heroesAttackType[6] + " не заблокировал удар!");
        }
        if (reply == 1) {
            System.out.println(heroesAttackType[6] + " заблокировал удар!");
            heroesHealth[6] = heroesHealth[6] + (bossDamage / 2);
            heroesHealth[6] = heroesHealth[6] + (bossDamage / 2) * 2;
        }
    }

    public static boolean thor() {
        boolean deafen = true;
        Random random = new Random();
        int reply = random.nextInt(2);
        if (heroesHealth[7] <= 0 && reply == 0) {
            bossDamage = 95;
            System.out.println(heroesAttackType[7] + " не получилось оглушить!");
            deafen = false;
        }
        if (heroesHealth[7] > 0 && reply == 0) {
            bossDamage = 95;
            System.out.println(heroesAttackType[7] + " не получилось оглушить!");
            deafen = false;
        }
        if (heroesHealth[7] > 0 && reply == 1) {
            bossDamage = 0;
            System.out.println(heroesAttackType[7] + " удачно оглушил Босса!");
            deafen = true;
        }
        return deafen;
    }
}
