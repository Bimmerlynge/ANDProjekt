package com.bimmerlynge.andprojekt.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bimmerlynge.andprojekt.R;
import com.bimmerlynge.andprojekt.databinding.FragmentHomeBinding;
import com.bimmerlynge.andprojekt.model.Group;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView rView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GroupViewModel groupViewModel =
                new ViewModelProvider(requireActivity()).get(GroupViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        rView = root.findViewById(R.id.listView);
        rView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        rView.hasFixedSize();

        ArrayList<Group> list = groupViewModel.getAllGroups();

        GroupAdapter adapter = new GroupAdapter(list);
        rView.setAdapter( adapter);
        rView.addOnItemTouchListener(new RecyclerTouchListener(getContext(),
                rView, (view, position) -> {
                    TextView group=(TextView) view.findViewById(R.id.group_name);
                    group.setOnClickListener(v -> {
                        Group selectedGroup = list.get(position);

                        groupViewModel.setCurrentGroup(selectedGroup);
                        Log.i("mig", "I Home Frag: " + groupViewModel.getCurrentGroup().toString());
                                Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_groupFragment);
                            }
                    );
                }));

        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public interface ClickListener{
        void onClick(View view, int position);
    }
    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

            this.clicklistener=clicklistener;
            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
//                    View child=recycleView.findChildViewUnder(e.getX(),e.getY());
//                    if(child!=null && clicklistener!=null){
//                        clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
//                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(e)){
                clicklistener.onClick(child,rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}