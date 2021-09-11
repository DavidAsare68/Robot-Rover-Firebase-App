package rc.rover;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import rc.rover.model.RoverStatus;
import rc.rover.model.UserAction;


public class Controls extends AppCompatActivity {

    private DatabaseReference myUserRef;
    private DatabaseReference myRoverRef;

    FirebaseAuth firebaseAuth;
    private TextView rpmView;
    private TextView distanceView;
    private View forwardBtn, backwardBtn, leftBtn, rightBtn, stopBtn;
    private RoverStatus roverStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controls);
        rpmView = this.findViewById(R.id.rpm);
        distanceView = this.findViewById(R.id.distance);
        forwardBtn = this.findViewById(R.id.forwardBtn);
        backwardBtn = this.findViewById(R.id.backwardBtn);
        leftBtn = this.findViewById(R.id.leftBtn);
        rightBtn = this.findViewById(R.id.rightBtn);
        stopBtn = this.findViewById(R.id.stopBtn);

        //NOTES: Process the button presses on the view
        View.OnClickListener listener = view -> {
            if (view.getId() == R.id.leftBtn) {
                sendAction(new UserAction("mini", UserAction.Direction.left, UserAction.Throttle.none));
            }
            if (view.getId() == R.id.rightBtn) {
                sendAction(new UserAction("mini", UserAction.Direction.right, UserAction.Throttle.none));
            }
            if (view.getId() == R.id.forwardBtn) {
                sendAction(new UserAction("mini", UserAction.Direction.none, UserAction.Throttle.forward));
            }
            if (view.getId() == R.id.backwardBtn) {
                sendAction(new UserAction("mini", UserAction.Direction.none, UserAction.Throttle.reverse));
            }
            if (view.getId() == R.id.stopBtn) {
                sendAction(new UserAction("mini", UserAction.Direction.none, UserAction.Throttle.none));
            }
        };



        //add the event listener to the buttons so that we can process when the user presses the button
        forwardBtn.setOnClickListener(listener);
        backwardBtn.setOnClickListener(listener);
        leftBtn.setOnClickListener(listener);
        rightBtn.setOnClickListener(listener);
        stopBtn.setOnClickListener(listener);

        //load the firebase database values and detect changes
        getDatabase();
        resetDataForUser();
        setUpDataListeners();
    }

    public void resetDataForUser() {
        if (firebaseAuth.getUid() != null) {
            myRoverRef.setValue(new RoverStatus());
        } else {
            Log.w("CONTROLS", "Unable to reset data. Please login first");
        }
    }

    /**
     * Used to send the user action to firebase to save
     * @param action the action pressed by the user
     */
    private void sendAction(UserAction action) {
        if (action == null) {
            return;
        }
        Log.i("CONTROLS", "Sending action " + action);
        if (firebaseAuth.getUid() != null) {
            //this line updates the firebase user node with the user action
            myUserRef.child(firebaseAuth.getUid()).setValue(action);
            Map<String, Object> status = new HashMap<>();
            status.put("throttle", action.getMotion());
            status.put("direction", action.getDirection());
            status.put("user", firebaseAuth.getUid());
            myRoverRef.updateChildren(status);
        } else {
            Log.w("CONTROLS", "Unable to send action. Please login first");
        }
    }

    /**
     * Load the firebase database references.
     */
    private void getDatabase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //point to the "mini" rover instance for information.
        myRoverRef = database.getReference("rover_status/mini");
        //point to the root node for all user actions
        myUserRef = database.getReference("user_action/");
    }

    private void setUpDataListeners(){
        //the rover information is stored on one-node. Get the RPM and distance anytime it changes
        myRoverRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                RoverStatus s = dataSnapshot.getValue(RoverStatus.class);
                assert s != null;

                if (roverStatus == null) {
                    roverStatus = s;
                }
                roverStatus.setDistance(s.getDistance());
                roverStatus.setRpm(s.getRpm());
                roverStatus.setRoverName(s.getRoverName());

                rpmView.setText(new DecimalFormat("#0.00").format(roverStatus.getRpm()));
                distanceView.setText(new DecimalFormat("#0.00").format(roverStatus.getDistance()));

                Log.d("STATUS", "status " + roverStatus.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read for rover status failed: " + databaseError.getCode());
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.controls_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.sign_out) {
            firebaseAuth.signOut();
            finish();
            Intent intent = new Intent(getApplicationContext(),Login.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}



