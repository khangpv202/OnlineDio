package com.example.OnlineDio.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import com.example.OnlineDio.R;
import com.example.OnlineDio.activity.CommentPostActivity;
import com.example.OnlineDio.model.CommentDTO;
import com.example.OnlineDio.util.ListCommentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 17/10/2013
 * Time: 10:07
 * To change this template use File | Settings | File Templates.
 */
public class CommentFragment extends Fragment
{
    private ListView listView;
    private EditText comment_edtComment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.comment_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.comment_lvComment);
        comment_edtComment = (EditText)view.findViewById(R.id.comment_edtComment);
        comment_edtComment.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getActivity(), CommentPostActivity.class);
                startActivityForResult(i, 1);
            }
        });
        List<CommentDTO> listComment = new ArrayList<CommentDTO>();
        CommentDTO commentDTO1 = new CommentDTO("asd ", "das ", "as ");
//        commentDTO1.setCommentIdImage(getResources().getIdentifier("",,));
        listComment.add(new CommentDTO("asd ", "das ", "as "));
        listComment.add(new CommentDTO(" asd", " as", " as"));
        listComment.add(new CommentDTO(" asdas", "asdas ", " asdas"));
        listComment.add(new CommentDTO(" ", " ", " "));
        listComment.add(new CommentDTO(" ", " ", " "));
        listComment.add(new CommentDTO(" ", " ", " "));
        listComment.add(new CommentDTO(" ", " ", " "));
        listComment.add(new CommentDTO(" ", " ", " "));
        listComment.add(new CommentDTO(" ", " ", " "));
        listComment.add(new CommentDTO(" ", " ", " "));
        listComment.add(new CommentDTO(" ", " ", " "));
        listComment.add(new CommentDTO(" ", " ", " "));
        listComment.add(new CommentDTO(" ", " ", " "));
        listComment.add(new CommentDTO(" ", " ", " "));
        listComment.add(new CommentDTO(" ", " ", " "));
        listComment.add(new CommentDTO(" ", " ", " "));
        listComment.add(new CommentDTO(" ", " ", " "));
        listComment.add(new CommentDTO(" ", " ", " "));
        listComment.add(new CommentDTO(" ", " ", " "));
        listComment.add(new CommentDTO(" ", " ", " "));
        ListCommentAdapter adapter = new ListCommentAdapter(getActivity(), R.layout.comment_row_custom, listComment);
        listView.setAdapter(adapter);
        return view;
    }
}
