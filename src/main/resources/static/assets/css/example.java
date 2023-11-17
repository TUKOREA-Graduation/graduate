Gson gson = new Gson();
JsonArray jArray = new JsonArray();

Iterator<LogVO> it = logNameList.iterator();
while(it.hasNext()) {
    LogVO curVO = it.next();
    JsonObject object = new JsonObject();
    String userid = curVO.getLog_userid();
    int cnt = curVO.getCnt();

        object.addProperty("ID", userid);
        object.addProperty("Count", cnt);
    jArray.add(object);
}

String json = gson.toJson(jArray);
model.addAttribute("json", json);