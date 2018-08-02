package dragons.android.bakingtime;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import dragons.android.bakingtime.model.Ingredient;



public class WidgetListService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        List<Ingredient> ingredients;

        String json = intent.getStringExtra("widgetIngredients");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Ingredient>>(){}.getType();
        ingredients = gson.fromJson(json,type);

        return new WidgetItemFactory(getApplicationContext(),intent, ingredients);
    }

    class WidgetItemFactory implements RemoteViewsFactory{

        private final Context context;
        @SuppressWarnings("unused")
        private final int appWidgetId;
        private final List<Ingredient> ingredientList;

        WidgetItemFactory(Context context, Intent intent, List<Ingredient> ingredients){

            this.context = context;
            this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            this.ingredientList = ingredients;

        }

        @Override
        public void onCreate() {


        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return ingredientList.size();
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.widget_list_item);

            views.setTextViewText(R.id.widget_ingredientName,ingredientList.get(i).getIngredient());
            views.setTextViewText(R.id.widget_ingredientQty, String.valueOf(ingredientList.get(i).getQuantity()));
            views.setTextViewText(R.id.widget_ingredientMeasure,ingredientList.get(i).getMeasure());

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
