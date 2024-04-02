package tn.esprit.tests;

import tn.esprit.entites.Destination;
import tn.esprit.entites.Guide;
import tn.esprit.services.GuideServices;

public class GuideTest {
    public static void main(String[] args) {
        GuideServices gs = new GuideServices();
         //Guide g1 = new Guide("hamdani","dhia","tunisie","tunisien","5ans",20.5,53501622);
         //gs.addGuide(g1);
        System.out.println(gs.getAllGuides());
        // int guideToDeleteId = 61;
        // gs.removeGuide(guideToDeleteId);

        int guideId = 63;
        Guide fetchedGuide = gs.getGuideById(guideId);
        if (fetchedGuide != null) {
            System.out.println("Guide found by ID: " + fetchedGuide);
        } else {
            System.out.println("No guide found with ID: " + guideId);
        }
        if (fetchedGuide != null) {
            fetchedGuide.setNationalite("Testing the update functionality on nationalite.");
            gs.updateGuide(fetchedGuide);
            System.out.println("Guide updated successfully: " + fetchedGuide);
        } else {
            System.out.println("No guide found for updating.");
        }
    }
}
