package com.jk.uitls;

public class BaseUtils {



//    private void handleReply(PostingComment comment, String idSons) {
//        //            判断是否有子评论
//        if ("0".equals(idSons)) {
////            递归的出口
//            comment.setSonComment(new ArrayList<>());
//        }
//        else {
////                存在子评论
////                查询子评论并存入 sonComment
//            List<PostingComment> sonComments = new ArrayList<>();
//            for (String idSon : idSons.split("_")) {
//                if ("0".equals(idSon))continue;
////                    查询结果 并存入 list
//                LambdaQueryWrapper<PostingComment> wrapperSonId = new LambdaQueryWrapper();
//
//                wrapperSonId.eq(PostingComment::getPostingId, comment.getPostingId());
//                wrapperSonId.eq(PostingComment::getCommentId, Integer.parseInt(idSon));
//                wrapperSonId.eq(PostingComment::getForbid,0);
//
//                PostingComment sonComment = getOne(wrapperSonId);
////                不为空 就添加 并寻找对应的子评论
//                if (sonComment != null) {
//                    sonComments.add(sonComment);
//                    handleReply(sonComment,sonComment.getIdSon());
//                }
//
//            }
////                保存完毕后进行存储
//
//            comment.setSonComment(sonComments);
//        }
//    }


}
