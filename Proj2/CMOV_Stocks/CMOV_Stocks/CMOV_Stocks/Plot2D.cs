﻿using SkiaSharp;
using System.Collections;

namespace CMOV_Stocks
{
    class Plot2D
    {
        public int height;
        public int width;

        ArrayList points = null;
        ArrayList points2 = null;

        ArrayList yLabels;
        ArrayList xLabels;

        SKCanvas canvas;

        public string Label1 = "Apples";
        public string Label2 = "Micros";

        public SKPaint paint1 = new SKPaint
        {
            IsAntialias = true,
            Style = SKPaintStyle.Fill,
            Color = new SKColor(94, 184, 255, 255),
            StrokeWidth = 2
        };

        public SKPaint topLinePaint1 = new SKPaint
        {
            IsAntialias = true,
            Style = SKPaintStyle.Stroke,
            Color = new SKColor(0, 91, 159),
            StrokeWidth = 3
        };

        public SKPaint paint2 = new SKPaint
        {
            IsAntialias = true,
            Style = SKPaintStyle.Fill,
            Color = new SKColor(255, 179, 0, 64),
            StrokeWidth = 2
        };

        public SKPaint topLinePaint2 = new SKPaint
        {
            IsAntialias = true,
            Style = SKPaintStyle.Stroke,
            Color = new SKColor(255, 111, 0),
            StrokeWidth = 3
        };


        public Plot2D(SKCanvas canvas, int height, int width)
        {
            this.canvas = canvas;
            this.height = height;
            this.width = width;

            yLabels = new ArrayList() { "One", "Two", "Three", "Four" };
            xLabels = new ArrayList() { "1", "2", "3" };

        }

        public Plot2D(SKCanvas sKCanvas, int height, int width, ArrayList yLabels, ArrayList xLabels, ArrayList points)
        {
            this.canvas = sKCanvas;
            this.height = height;
            this.width = width;
            this.yLabels = yLabels;
            this.xLabels = xLabels;
            this.points = points;
        }

        public Plot2D(SKCanvas sKCanvas, int height, int width, ArrayList yLabels, ArrayList xLabels, ArrayList points, ArrayList points2)
        {
            this.canvas = sKCanvas;
            this.height = height;
            this.width = width;
            this.yLabels = yLabels;
            this.xLabels = xLabels;
            this.points = points;
            this.points2 = points2;
        }


        public void Display2Graphs()
        {


            float MaxValue = Utils.FindBoundValue(points, true);
            float MinValue = Utils.FindBoundValue(points, false);
            if (MaxValue < Utils.FindBoundValue(points2, true))
                MaxValue = Utils.FindBoundValue(points2, true);
            if (MinValue > Utils.FindBoundValue(points2, false))
                MinValue = Utils.FindBoundValue(points2, false);

            this.DrawGraphExisAndLables();
            this.PlotStockEvolution(points, 1, MaxValue, MinValue);
            this.PlotStockEvolution(points2, 2, MaxValue, MinValue);
            DrawColorLabels();
        }

        public void Display()
        {
            if (points == null)
            {
                DisplayGraph();
                return;
            }

            if (points2 != null)
            {
                Display2Graphs();
                return;
            }
            float MaxValue = Utils.FindBoundValue(points, true);
            float MinValue = Utils.FindBoundValue(points, false);
            this.DrawGraphExisAndLables();
            this.PlotStockEvolution(points, 1, MaxValue, MinValue);
            DrawColorLabels();
            this.DrawLinearRegression(points, MaxValue, MinValue);

        }


        public void DisplayGraph()
        {
            this.DrawGraphExisAndLables();
            DrawColorLabels();
        }





        public void SetFillColor1(SKPaint sKPaint)
        {
            paint1 = sKPaint;
        }

        public void SetFillColor2(SKPaint sKPaint)
        {
            paint2 = sKPaint;
        }

        public void SetTopColor1(SKPaint sKPaint)
        {
            topLinePaint1 = sKPaint;
        }

        public void SetTopColor2(SKPaint sKPaint)
        {
            topLinePaint2 = sKPaint;
        }

        private void PlotStockEvolution(ArrayList points, int no, float MaxValue, float MinValue)
        {

            float value_offset = MaxValue - MinValue;


            var paddingHeight = height / 8;
            var paddingWidht = width / 8;

            var bottom = 2 * paddingHeight + height / 2; ;

            int levels = xLabels.Count;
            int realHeight = height / 2;
            int realWidth = 3 * width / 4;
            int y_offset = height / levels / 2;
            int dy = realHeight - y_offset;




            var path = new SKPath();
            var topLine = new SKPath();

            path.MoveTo(paddingWidht, bottom);
            path.LineTo(paddingWidht, bottom - (y_offset + dy * (((float)points[0] - MinValue) / value_offset)));
            topLine.MoveTo(paddingWidht, bottom - (y_offset + dy * (((float)points[0] - MinValue) / value_offset)));

            for (int i = 1; i < points.Count; i++)
            {
                path.LineTo(paddingWidht + realWidth * i / (points.Count - 1), bottom - (y_offset + dy * (((float)points[i] - MinValue) / value_offset)));
                topLine.LineTo(paddingWidht + realWidth * i / (points.Count - 1), bottom - (y_offset + dy * (((float)points[i] - MinValue) / value_offset)));
            }
            path.LineTo(paddingWidht + realWidth, bottom);
            path.LineTo(paddingWidht, bottom);

            if (no == 1)
            {
                canvas.DrawPath(path, paint1);
                canvas.DrawPath(topLine, topLinePaint1);
            }
            else
            {
                canvas.DrawPath(path, paint2);
                canvas.DrawPath(topLine, topLinePaint2);
            }

        }


        private void DrawLinearRegression(ArrayList points, float MaxValue, float MinValue)
        {
            float value_offset = MaxValue - MinValue;


            var paddingHeight = height / 8;
            var paddingWidht = width / 8;

            var bottom = 2 * paddingHeight + height / 2; ;

            int levels = xLabels.Count;
            int realHeight = height / 2;
            int realWidth = 3 * width / 4;
            int y_offset = height / levels / 2;
            int dy = realHeight - y_offset;


            int n = points.Count;
            float Ey = 0;
            float Ex = 0;
            float Exy = 0;
            float Ey2 = 0;
            float Ex2 = 0;

            for (int i = 0; i < n; i++)
            {
                Ey += (float)points[i];
                Ex += i;
                Ex2 += i * i;
                Ey2 += (float)points[i] * (float)points[i];
                Exy += (float)points[i] * i;
            }

            float a = (Ey * Ex2 - Ex * Exy) / (n * Ex2 - Ex * Ex);
            float b = (n * (Exy) - Ex * Ey) / (n * Ex2 - Ex * Ex);

            float y1 = a;
            float y2 = a + b * (n - 1);


            SKPaint paintR = new SKPaint
            {
                IsAntialias = true,
                Style = SKPaintStyle.Stroke,
                Color = new SKColor(255, 111, 0),
                StrokeWidth = 4
            };
            var topLine = new SKPath();
            topLine.MoveTo(paddingWidht, bottom - (y_offset + dy * (((float)y1 - MinValue) / value_offset)));
            topLine.LineTo(paddingWidht + realWidth, bottom - (y_offset + dy * (((float)y2 - MinValue) / value_offset)));
            canvas.DrawPath(topLine, paintR);
        }


        private void DrawGraphExisAndLables()
        {



            var paddingHeight = height / 8;
            var paddingWidht = width / 8;

            var bottom = 2 * paddingHeight + height / 2;
            var bottom_text = 5 * paddingHeight / 2 + height / 2;

            var realHeight = height / 2;
            var realWidth = paddingWidht + 3 * width / 4;


            // create the paint for the path
            var x_axis = new SKPaint
            {
                IsAntialias = true,
                Style = SKPaintStyle.Stroke,
                Color = SKColors.Black,
                StrokeWidth = 5
            };

            var x_axis_support = new SKPaint
            {
                IsAntialias = true,
                Style = SKPaintStyle.Stroke,
                Color = new SKColor(136, 136, 136),
                StrokeWidth = 1

            };



            var depth = bottom;
            string text;
            int xlevels = xLabels.Count;

            for (var i = 0; i <= xlevels; i++)
            {


                if (i == 0)
                {
                    // x-line bottom
                    using (var path = new SKPath())
                    {
                        path.MoveTo(paddingWidht, bottom);
                        path.LineTo(realWidth, bottom);
                        canvas.DrawPath(path, x_axis);
                    }
                }
                else
                {

                    using (var path = new SKPath())
                    {
                        path.MoveTo(paddingWidht, depth);
                        path.LineTo(realWidth, depth);
                        canvas.DrawPath(path, x_axis_support);
                    }
                    // text
                    using (SKPaint paint = new SKPaint())
                    {

                        text = (string)xLabels[i - 1];
                        paint.TextSize = 25;
                        string text1 = "" + text;

                        float textWidth = paint.MeasureText(text1);

                        SKRect textBounds = new SKRect();
                        paint.MeasureText("100", ref textBounds);

                        float xText = realWidth + textBounds.MidX / 2;
                        float yText = depth - textBounds.MidY;

                        paint.Color = new SKColor(136, 136, 136);
                        canvas.DrawText(text1, xText, yText, paint);
                    }
                }
                depth -= realHeight / xlevels;

            }

            depth = paddingWidht;
            int ylevels = yLabels.Count;
            for (var i = 0; i < ylevels; i++)
            {
                using (var path = new SKPath())
                {
                    path.MoveTo(depth, bottom);
                    path.LineTo(depth, bottom - realHeight);
                    canvas.DrawPath(path, x_axis_support);
                }

                // text
                using (SKPaint paint = new SKPaint())
                {

                    text = (string)yLabels[i];

                    // AQUI!!!!
                    if (text.Equals(""))
                    {
                        depth += realWidth / ylevels;
                        continue;
                    }


                    paint.TextSize = 25;
                    string text1 = "" + text;

                    float textWidth = paint.MeasureText(text1);

                    SKRect textBounds = new SKRect();
                    paint.MeasureText(text1, ref textBounds);

                    float xText = depth - textBounds.MidX;
                    float yText = bottom_text - textBounds.MidY;

                    paint.Color = new SKColor(136, 136, 136);
                    canvas.DrawText(text1, xText, yText, paint);
                }
                depth += realWidth / ylevels;

            }
        }

        private void DrawColorLabels()
        {
            var paddingHeight = height / 8;

            var xCenter = width / 2;
            var padding = width / 20;

            using (SKPaint paint = new SKPaint())
            {
                // Square
                SKPaint skPaint = new SKPaint()
                {
                    Style = SKPaintStyle.Fill,
                    Color = topLinePaint1.Color,
                    StrokeWidth = 10,
                    IsAntialias = true,
                };

                SKRect skRectangle = new SKRect();
                skRectangle.Size = new SKSize(30, 30);



                // Text
                string text = Label1;
                paint.TextSize = 40;

                float textWidth = paint.MeasureText(text);

                SKRect textBounds = new SKRect();
                paint.MeasureText(text, ref textBounds);


                float yText = paddingHeight;

                paint.Color = new SKColor(136, 136, 136);



                if (points2 == null)
                {
                    int diff = (30 + padding + (int)textWidth) / 2;
                    skRectangle.Location = new SKPoint(xCenter - diff, paddingHeight - 30);
                    canvas.DrawRect(skRectangle, skPaint);
                    canvas.DrawText(text, xCenter - diff + 30 + padding, yText, paint);
                    return;
                }

                // Square
                SKPaint skPaint2 = new SKPaint()
                {
                    Style = SKPaintStyle.Fill,
                    Color = topLinePaint2.Color,
                    StrokeWidth = 10,
                    IsAntialias = true,
                };

                SKRect skRectangle2 = new SKRect();
                skRectangle2.Size = new SKSize(30, 30);

                // Text
                string text2 = Label2;
                paint.TextSize = 40;

                float textWidth2 = paint.MeasureText(text);

                SKRect textBounds2 = new SKRect();
                paint.MeasureText(text, ref textBounds2);

                int diff2 = (2 * 30 + 4 * padding + (int)textWidth + (int)textWidth2) / 2;
                skRectangle.Location = new SKPoint(xCenter - diff2, paddingHeight - 30);
                canvas.DrawRect(skRectangle, skPaint);
                canvas.DrawText(text, xCenter - diff2 + 30 + padding, yText, paint);
                skRectangle2.Location = new SKPoint(xCenter - diff2 + 60 + 3 * padding + (int)textWidth, paddingHeight - 30);
                canvas.DrawRect(skRectangle2, skPaint2);
                canvas.DrawText(text2, xCenter - diff2 + 60 + 4 * padding + (int)textWidth, yText, paint);
            }
        }
    }
}