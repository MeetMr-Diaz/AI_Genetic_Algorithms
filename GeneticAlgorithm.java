import java.util.*;
import java.util.stream.Collectors;

public class GeneticAlgorithm {
    public static void main(String[] args) {
        int generations = 100;
        double mutationRate = 0.01;
        int n = 100;

        List<String> Facilitators = new ArrayList<>();
        // Define facilitators
        Facilitators.add("Lock");
        Facilitators.add("Glen");
        Facilitators.add("Glen");
        Facilitators.add("Glen");
        Facilitators.add("Shaw");
        Facilitators.add("Singer");
        Facilitators.add("Uther");
        Facilitators.add("Numen");
        Facilitators.add("Zeldin");

        // Define activity data
        Map<String, Object> SLA100A = new HashMap<>();
        SLA100A.put("enrollment", 50);
        SLA100A.put("preferred_facilitators", Arrays.asList("Glen", "Lock", "Banks", "Zeldin"));
        SLA100A.put("other_facilitators", Arrays.asList("Numen", "Richards"));

        Map<String, Object> SLA100B = new HashMap<>();
        SLA100B.put("enrollment", 50);
        SLA100B.put("preferred_facilitators", Arrays.asList("Glen", "Lock", "Banks", "Zeldin"));
        SLA100B.put("other_facilitators", Arrays.asList("Numen", "Richards"));

        Map<String, Object> SLA191A = new HashMap<>();
        SLA191A.put("enrollment", 50);
        SLA191A.put("preferred_facilitators", Arrays.asList("Glen", "Lock", "Banks", "Zeldin"));
        SLA191A.put("other_facilitators", Arrays.asList("Numen", "Richards"));

        Map<String, Object> SLA191B = new HashMap<>();
        SLA191B.put("enrollment", 50);
        SLA191B.put("preferred_facilitators", Arrays.asList("Glen", "Lock", "Banks", "Zeldin"));
        SLA191B.put("other_facilitators", Arrays.asList("Numen", "Richards"));

        Map<String, Object> SLA201 = new HashMap<>();
        SLA201.put("enrollment", 50);
        SLA201.put("preferred_facilitators", Arrays.asList("Glen", "Lock", "Banks", "Zeldin"));
        SLA201.put("other_facilitators", Arrays.asList("Numen", "Richards", "Singer"));

        Map<String, Object> SLA291 = new HashMap<>();
        SLA291.put("enrollment", 50);
        SLA291.put("preferred_facilitators", Arrays.asList("Lock", "Banks", "Zeldin", "Singer"));
        SLA291.put("other_facilitators", Arrays.asList("Numen", "Richards", "Shaw", "Tyler"));

        Map<String, Object> SLA303 = new HashMap<>();
        SLA303.put("enrollment", 60);
        SLA303.put("preferred_facilitators", Arrays.asList("Glen", "Zeldin", "Banks"));
        SLA303.put("other_facilitators", Arrays.asList("Numen", "Singer", "Shaw"));

        Map<String, Object> SLA304 = new HashMap<>();
        SLA304.put("enrollment", 25);
        SLA304.put("preferred_facilitators", Arrays.asList("Glen", "Banks", "Tyler"));
        SLA304.put("other_facilitators", Arrays.asList("Numen", "Singer", "Shaw", "Richards", "Uther", "Zeldin"));

        Map<String, Object> SLA394 = new HashMap<>();
        SLA394.put("enrollment", 20);
        SLA394.put("preferred_facilitators", Arrays.asList("Tyler", "Singer"));
        SLA394.put("other_facilitators", Arrays.asList("Richards", "Zeldin"));

        Map<String, Object> SLA449 = new HashMap<>();
        SLA449.put("enrollment", 60);
        SLA449.put("preferred_facilitators", Arrays.asList("Tyler", "Singer", "Shaw"));
        SLA449.put("other_facilitators", Arrays.asList("Zeldin", "Uther"));

        Map<String, Object> SLA451 = new HashMap<>();
        SLA451.put("enrollment", 60);
        SLA451.put("preferred_facilitators", Arrays.asList("Tyler", "Singer", "Shaw"));
        SLA451.put("other_facilitators", Arrays.asList("Zeldin", "Uther", "Richards", "Banks"));

        Map<String, Integer> Rooms = new HashMap<>();
        // Define room data
        Rooms.put("Slate 003", 45);
        Rooms.put("Roman 216", 30);
        Rooms.put("Loft 206", 75);
        Rooms.put("Roman 201", 50);
        Rooms.put("Loft 310", 108);
        Rooms.put("Beach 201", 60);
        Rooms.put("Beach 301", 75);
        Rooms.put("Logos 325", 450);
        Rooms.put("Frank 119", 60);

        // Define times
        List<Integer> Times = Arrays.asList(10, 11, 12, 13, 14, 15);

        Map<String, Map<String, Object>> Activities = new HashMap<>();
        // Add the activity data to the Activities map
        Activities.put("SLA100A", SLA100A);
        Activities.put("SLA100B", SLA100B);
        Activities.put("SLA191A", SLA191A);
        Activities.put("SLA191B", SLA191B);
        Activities.put("SLA201", SLA201);
        Activities.put("SLA291", SLA291);
        Activities.put("SLA303", SLA303);
        Activities.put("SLA304", SLA304);
        Activities.put("SLA394", SLA394);
        Activities.put("SLA449", SLA449);
        Activities.put("SLA451", SLA451);

        List<Map<String, Object>> population = createInitialPopulation(n, Activities, Rooms, Times, Facilitators);
        Map<String, Object> bestSchedule = geneticAlgorithm(population, generations, mutationRate, Activities, Rooms,
                Times, Facilitators);
        displaySchedule(bestSchedule);
    }

    // Function to generate a random schedule for each activity
    private static Map<String, Object> generateRandomSchedule(
            Map<String, Map<String, Object>> Activities,
            Map<String, Integer> Rooms, List<Integer> Times, List<String> Facilitators) {
            Map<String, Object> schedule = new HashMap<>();
        Random random = new Random();
        for (String activity : Activities.keySet()) {
            String room = Rooms.keySet().toArray(new String[0])[random.nextInt(Rooms.size())];
            int time = Times.get(random.nextInt(Times.size()));
            String facilitator = Facilitators.get(random.nextInt(Facilitators.size()));
            schedule.put(activity, Map.of("room", room, "time", time, "facilitator", facilitator));
        }
        return schedule;
    }

    // Function to create the initial population
    private static List<Map<String, Object>> createInitialPopulation(
            int n,
            Map<String, Map<String, Object>> Activities,
            Map<String, Integer> Rooms,
            List<Integer> Times,
            List<String> Facilitators) {
            List<Map<String, Object>> population = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            population.add(generateRandomSchedule(Activities, Rooms, Times, Facilitators));
        }
        return population;
    }

    // Function to calculate the fitness score for each activity
    private static double fitnessFunction(
            Map<String, Object> schedule,
            Map<String, Map<String, Object>> Activities,
            Map<String, Integer> Rooms) {
        // Implement your fitness function here
        double score = 0.0;
        Map<String, Integer> facilitatorsLoad = new HashMap<>();

        for (String activity : schedule.keySet()) {
            Map<String, Object> activityInfo = (Map<String, Object>) schedule.get(activity);
            Map<String, Object> activityData = Activities.get(activity);

            //checking time if int
            Object timeObj = activityInfo.get("time");
            int time;
      
            if (timeObj instanceof Integer) {
                time = (int) timeObj;
            } else if (timeObj instanceof String) {
                try {
                    time = Integer.parseInt((String) timeObj);
                } catch (NumberFormatException e) {
                    time = -1; // Or any default value that fits your requirements
                }
            } else {
                time = -1; // Or any default value that suits your needs
            }

            int expectedEnrollment = (int) activityData.get("enrollment");
            double activityScore = 0.0;

            for (String other : schedule.keySet()) {
                if (!activity.equals(other) &&
                        activityInfo.get("room").equals(((Map<String, Object>) schedule.get(other)).get("room")) &&
                        activityInfo.get("time").equals(((Map<String, Object>) schedule.get(other)).get("time"))) {
                    activityScore -= 0.5;
                }
            }

            String roomName = (String) activityInfo.get("room");
            int roomCapacity = Rooms.get(roomName);

            //checking for room size activities
            if (roomCapacity < expectedEnrollment) {
                activityScore -= 0.5;
            } else if (roomCapacity > 3 * expectedEnrollment) {
                activityScore -= 0.2;
            } else if (roomCapacity > 6 * expectedEnrollment) {
                activityScore -= 0.4;
            } else {
                activityScore += 0.3;
            }

            String facilitator = (String) activityInfo.get("facilitator");
            List<String> preferredFacilitators = (List<String>) activityData.get("preferred_facilitators");
            List<String> otherFacilitators = (List<String>) activityData.get("other_facilitators");

            //checking for activities is overseen
            if (preferredFacilitators.contains(facilitator)) {
                activityScore += 0.5;
            } else if (otherFacilitators.contains(facilitator)) {
                activityScore += 0.2;
            } else {
                activityScore -= 0.1;
            }

            facilitatorsLoad.put(facilitator, facilitatorsLoad.getOrDefault(facilitator, 0) + 1);

            //checking for facilitator workload
            if (facilitatorsLoad.get(facilitator) == 1) {
                activityScore += 0.2;
            } else if (facilitatorsLoad.get(facilitator) > 1) {
                activityScore -= 0.2;
            }

            //special case for Dr. Tyler
            if (!"Dr. Tyler".equals(facilitator) && facilitatorsLoad.get(facilitator) > 4) {
                activityScore -= 0.5;
            }

            if ("Dr. Tyler".equals(facilitator) && facilitatorsLoad.get(facilitator) > 2) {
                activityScore -= 0.4;
            }

            // Activity-specific adjustments
            if ("SLA101A".equals(activity) || "SLA101B".equals(activity)) {
                for (String otherActivity : schedule.keySet()) {
                    if (("SLA101A".equals(otherActivity) || "SLA101B".equals(otherActivity))
                            && !activity.equals(otherActivity)) {

                        //special case for this one class that has 2 sections
                        int timeDiff = Math.abs((int) activityInfo.get("time") - (int) ((Map<String, Integer>) schedule.get(otherActivity)).get("time"));

                        if (timeDiff > 4) {
                            activityScore += 0.5;
                        } else if (timeDiff == 0) {
                            activityScore -= 0.5;
                        }
                    }
                }
            } else if ("SLA191A".equals(activity) || "SLA191B".equals(activity)) {
                for (String otherActivity : schedule.keySet()) {

                    if (("SLA191A".equals(otherActivity) || "SLA191B".equals(otherActivity))
                            && !activity.equals(otherActivity)) {
                                int timeDiff = Math.abs((int) activityInfo.get("time") - (int) ((Map<String, Object>) schedule.get(otherActivity)).get("time")); //check type first

                        // error is happening here.....solution just copy if statement from class above/ has correct wat to call for time
                        if (timeDiff > 4) {
                            activityScore += 0.5;
                        } else if (timeDiff == 0) {
                            activityScore -= 0.5;
                        }
                    }
                }

                for (String otherActivity : schedule.keySet()) {
                    if (("SLA101A".equals(otherActivity) || "SLA101B".equals(otherActivity))
                            && Math.abs(Integer.parseInt((String) activityInfo.get("time")) - Integer.parseInt(
                                    (String) ((Map<String, Object>) schedule.get(otherActivity)).get("time"))) == 1) {
                        if (((String) activityInfo.get("room")).contains("Roman")
                                || ((String) activityInfo.get("room")).contains("Beach")) {
                            if (!((String) ((Map<String, Object>) schedule.get(otherActivity)).get("room"))
                                    .contains("Roman")
                                    && !((String) ((Map<String, Object>) schedule.get(otherActivity)).get("room"))
                                            .contains("Beach")) {
                                activityScore -= 0.4;
                            }
                        } else {
                            activityScore += 0.5;
                        }
                    } else if (("SLA101A".equals(otherActivity) || "SLA101B".equals(otherActivity))
                            && ((String) activityInfo.get("time")).equals(
                                    ((String) ((Map<String, Object>) schedule.get(otherActivity)).get("time")))) {
                        activityScore -= 0.25;
                    } else if (("SLA101A".equals(otherActivity) || "SLA101B".equals(otherActivity))
                            && Math.abs(Integer.parseInt((String) activityInfo.get("time")) - Integer.parseInt(
                                    (String) ((Map<String, Object>) schedule.get(otherActivity)).get("time"))) == 2) {
                        activityScore += 0.25;
                    }
                }
            }

            score += activityScore;
        }
        System.out.println(score);
        return score;
    }

    // Function to perform softmax normalization
    private static List<Double> softmax(List<Double> x) {
        double sumExp = x.stream().mapToDouble(Math::exp).sum();
        return x.stream().map(score -> Math.exp(score) / sumExp).collect(Collectors.toList());
    }

    // Function for selecting parents based on softmax probabilities
    private static List<Map<String, Object>> selection(
            List<Map<String, Object>> population,
            Map<String, Map<String, Object>> Activities, Map<String, Integer> Rooms) {
            List<Double> fitnessScores = population.stream()
                                                            .map(schedule -> fitnessFunction(schedule, Activities, Rooms))
                                                            .collect(Collectors.toList());

            List<Double> probabilities = softmax(fitnessScores);
            List<Map<String, Object>> selectedPopulation = new ArrayList<>();

        for (int i = 0; i < population.size(); i++) {
            double r = Math.random();
            double cumulativeProbability = 0;
            for (int j = 0; j < population.size(); j++) {
                cumulativeProbability += probabilities.get(j);
                if (r <= cumulativeProbability) {
                    selectedPopulation.add(population.get(j));
                    break;
                }
            }
        }

        return selectedPopulation;
    }

    // Function for performing crossover
    private static List<Map<String, Object>> crossover(List<Map<String, Object>> parents) {
        List<Map<String, Object>> offspring = new ArrayList<>();

        // Randomly select two parents for crossover
        Random random = new Random();
        int parentIndex1 = random.nextInt(parents.size());
        int parentIndex2;
        do {
            parentIndex2 = random.nextInt(parents.size());
        } while (parentIndex2 == parentIndex1);

        Map<String, Object> parent1 = parents.get(parentIndex1);
        Map<String, Object> parent2 = parents.get(parentIndex2);

        // Create two new schedules by swapping activities between parents
        Map<String, Object> offspring1 = new HashMap<>();
        Map<String, Object> offspring2 = new HashMap<>();

        for (String activity : parent1.keySet()) {
            if (random.nextBoolean()) {
                offspring1.put(activity, parent1.get(activity));
                offspring2.put(activity, parent2.get(activity));
            } else {
                offspring1.put(activity, parent2.get(activity));
                offspring2.put(activity, parent1.get(activity));
            }
        }

        offspring.add(offspring1);
        offspring.add(offspring2);

        return offspring;
    }

    // Function for applying mutation
    private static List<Map<String, Object>> mutation(
            List<Map<String, Object>> offspring, double mutationRate,
            Map<String, Map<String, Object>> Activities, Map<String, Integer> Rooms, List<Integer> Times,
            List<String> Facilitators) {
            List<Map<String, Object>> mutatedOffspring = new ArrayList<>();

            Random random = new Random();

        for (Map<String, Object> schedule : offspring) {
            if (random.nextDouble() < mutationRate) {
                // Apply mutation to this schedule

                // Select a random activity from the schedule
                List<String> activityList = new ArrayList<>(schedule.keySet());
                String activityToMutate = activityList.get(random.nextInt(activityList.size()));

                // Generate a new random schedule for the selected activity
                String room = new ArrayList<>(Rooms.keySet()).get(random.nextInt(Rooms.size()));
                int time = Times.get(random.nextInt(Times.size()));
                String facilitator = Facilitators.get(random.nextInt(Facilitators.size()));

                // Update the schedule with the mutated activity
                schedule.put(activityToMutate, Map.of("room", room, "time", time, "facilitator", facilitator));
            }

            mutatedOffspring.add(schedule);
        }

        return mutatedOffspring;
    }

    // Main genetic algorithm function
    private static Map<String, Object> geneticAlgorithm(List<Map<String, Object>> population, int generations,
            double mutationRate, Map<String, Map<String, Object>> Activities, Map<String, Integer> Rooms,
            List<Integer> Times, List<String> Facilitators) {
        for (int generation = 0; generation < generations; generation++) {
            List<Map<String, Object>> newPopulation = new ArrayList<>();

            while (newPopulation.size() < population.size()) {
                List<Map<String, Object>> parents = selection(population, Activities, Rooms);
                List<Map<String, Object>> offspring = crossover(parents);
                offspring = mutation(offspring, mutationRate, Activities, Rooms, Times, Facilitators);
                newPopulation.addAll(offspring);
            }

            population = newPopulation;
        }

        // Find and return the best schedule
        Map<String, Object> bestSchedule = population.stream()
                .max(Comparator.comparingDouble(schedule -> fitnessFunction(schedule, Activities, Rooms)))
                .orElse(null);

        return bestSchedule;
    }

    // Function to display the schedule
    private static void displaySchedule(Map<String, Object> schedule) {
        System.out.println("\nThe best schedule generated by the genetic algorithm is: \n");
        System.out.println(String.format("%-15s %-15s %-15s %-15s", "Activity", "Room", "Time", "Facilitator"));
        System.out.println("\n--------------------------------------------------------");

        for (Map.Entry<String, Object> entry : schedule.entrySet()) {
            String activity = entry.getKey();
            Map<String, Object> activityInfo = (Map<String, Object>) entry.getValue();

            String room = (String) activityInfo.get("room");

            // Check the type of the "time" attribute before casting
            Object timeObj = activityInfo.get("time");
            int time;
            if (timeObj instanceof Integer) {
                time = (int) timeObj;
            } else if (timeObj instanceof String) {
                time = Integer.parseInt((String) timeObj);
            } else {
                // Handle the case where the type of "time" is unexpected
                time = -1; // Or any default value or error handling that suits your needs
            }

            String facilitator = (String) activityInfo.get("facilitator");

            System.out.println(String.format("%-15s %-15s %-15d %-15s", activity, room, time, facilitator));

        }
    }
}
